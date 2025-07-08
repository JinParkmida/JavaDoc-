import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class JavaDocGenerator {
    private static final String INDENT = "    ";
    private int processedFiles = 0;
    private int addedDocs = 0;

    public static void main(String[] args) {
        JavaDocGenerator generator = new JavaDocGenerator();
        
        if (args.length == 0) {
            System.out.println("Usage: java JavaDocGenerator <directory-path>");
            System.out.println("Example: java JavaDocGenerator ./src");
            return;
        }

        String path = args[0];
        generator.processDirectory(path);
    }

    public void processDirectory(String directoryPath) {
        System.out.println("🔍 Scanning directory: " + directoryPath);
        System.out.println("================================");
        
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(this::processFile);
            
            System.out.println("================================");
            System.out.println("✅ Processing complete!");
            System.out.println("📊 Files processed: " + processedFiles);
            System.out.println("📝 JavaDocs added: " + addedDocs);
        } catch (IOException e) {
            System.err.println("❌ Error scanning directory: " + e.getMessage());
        }
    }

    private void processFile(Path filePath) {
        System.out.println("📄 Processing: " + filePath.getFileName());
        processedFiles++;
        
        try {
            String content = Files.readString(filePath);
            String documented = addJavaDocComments(content);
            
            if (!content.equals(documented)) {
                // Create backup
                Path backupPath = Paths.get(filePath.toString() + ".backup");
                Files.writeString(backupPath, content);
                
                // Write documented version
                Files.writeString(filePath, documented);
                System.out.println("   ✓ JavaDocs added to " + filePath.getFileName());
            } else {
                System.out.println("   - Already documented or no changes needed");
            }
        } catch (IOException e) {
            System.err.println("   ❌ Error processing file: " + e.getMessage());
        }
    }

    private String addJavaDocComments(String content) {
        StringBuilder result = new StringBuilder();
        String[] lines = content.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String trimmed = line.trim();
            
            // Check if this line needs JavaDoc
            if (needsJavaDoc(trimmed, i > 0 ? lines[i-1] : "")) {
                String indentation = getIndentation(line);
                String javaDoc = generateJavaDoc(trimmed, indentation);
                
                if (!javaDoc.isEmpty()) {
                    result.append(javaDoc);
                    addedDocs++;
                }
            }
            
            result.append(line).append("\n");
        }
        
        return result.toString();
    }

    private boolean needsJavaDoc(String line, String previousLine) {
        // Skip if already has JavaDoc
        if (previousLine.trim().endsWith("*/")) {
            return false;
        }
        
        // Check for class declaration
        if (line.matches("^(public|private|protected)?\\s*(static)?\\s*(final)?\\s*class\\s+\\w+.*")) {
            return true;
        }
        
        // Check for interface declaration
        if (line.matches("^(public|private|protected)?\\s*interface\\s+\\w+.*")) {
            return true;
        }
        
        // Check for method declaration
        if (line.matches("^(public|private|protected)?\\s*(static)?\\s*(final)?\\s*\\w+\\s+\\w+\\s*\\(.*\\).*")) {
            return !line.contains("new ") && !line.contains("=");
        }
        
        // Check for field declaration
        if (line.matches("^(public|private|protected)?\\s*(static)?\\s*(final)?\\s*\\w+\\s+\\w+\\s*(=.*)?;")) {
            return true;
        }
        
        return false;
    }

    private String generateJavaDoc(String line, String indentation) {
        StringBuilder doc = new StringBuilder();
        
        if (line.contains("class ")) {
            String className = extractClassName(line);
            doc.append(indentation).append("/**\n");
            doc.append(indentation).append(" * ").append(className).append(" class.\n");
            doc.append(indentation).append(" * TODO: Add detailed description\n");
            doc.append(indentation).append(" * \n");
            doc.append(indentation).append(" * @author TODO\n");
            doc.append(indentation).append(" * @version 1.0\n");
            doc.append(indentation).append(" */\n");
        } 
        else if (line.contains("interface ")) {
            String interfaceName = extractInterfaceName(line);
            doc.append(indentation).append("/**\n");
            doc.append(indentation).append(" * ").append(interfaceName).append(" interface.\n");
            doc.append(indentation).append(" * TODO: Add detailed description\n");
            doc.append(indentation).append(" */\n");
        }
        else if (isMethodDeclaration(line)) {
            MethodInfo method = parseMethod(line);
            doc.append(indentation).append("/**\n");
            doc.append(indentation).append(" * ").append(generateMethodSummary(method)).append("\n");
            
            // Add @param tags
            for (Parameter param : method.parameters) {
                doc.append(indentation).append(" * @param ").append(param.name)
                   .append(" ").append(generateParamDescription(param)).append("\n");
            }
            
            // Add @return tag if not void
            if (!method.returnType.equals("void")) {
                doc.append(indentation).append(" * @return ").append(generateReturnDescription(method.returnType)).append("\n");
            }
            
            // Add @throws if needed
            if (line.contains("throws")) {
                String exceptions = extractExceptions(line);
                for (String exception : exceptions.split(",")) {
                    doc.append(indentation).append(" * @throws ").append(exception.trim())
                       .append(" if TODO\n");
                }
            }
            
            doc.append(indentation).append(" */\n");
        }
        else if (isFieldDeclaration(line)) {
            String fieldName = extractFieldName(line);
            String fieldType = extractFieldType(line);
            doc.append(indentation).append("/**\n");
            doc.append(indentation).append(" * ").append(generateFieldDescription(fieldName, fieldType)).append("\n");
            doc.append(indentation).append(" */\n");
        }
        
        return doc.toString();
    }

    private String extractClassName(String line) {
        Pattern pattern = Pattern.compile("class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : "Unknown";
    }

    private String extractInterfaceName(String line) {
        Pattern pattern = Pattern.compile("interface\\s+(\\w+)");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : "Unknown";
    }

    private String extractFieldName(String line) {
        // Remove access modifiers and type info to get field name
        String cleaned = line.replaceAll("^(public|private|protected)?\\s*(static)?\\s*(final)?\\s*\\w+\\s+", "");
        cleaned = cleaned.replaceAll("\\s*=.*", "").replaceAll(";", "").trim();
        return cleaned;
    }

    private String extractFieldType(String line) {
        Pattern pattern = Pattern.compile("(\\w+)\\s+\\w+\\s*(=|;)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown";
    }

    private boolean isMethodDeclaration(String line) {
        return line.matches(".*\\w+\\s*\\(.*\\).*") && 
               !line.contains("new ") && 
               !line.contains("=") &&
               !line.contains("if") &&
               !line.contains("while") &&
               !line.contains("for");
    }

    private boolean isFieldDeclaration(String line) {
        return line.endsWith(";") && !line.contains("(") && !line.contains(")");
    }

    private MethodInfo parseMethod(String line) {
        MethodInfo method = new MethodInfo();
        
        // Extract return type and method name
        Pattern pattern = Pattern.compile("(\\w+)\\s+(\\w+)\\s*\\(");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            method.returnType = matcher.group(1);
            method.name = matcher.group(2);
        }
        
        // Extract parameters
        Pattern paramPattern = Pattern.compile("\\((.*?)\\)");
        Matcher paramMatcher = paramPattern.matcher(line);
        if (paramMatcher.find()) {
            String paramString = paramMatcher.group(1);
            if (!paramString.trim().isEmpty()) {
                String[] params = paramString.split(",");
                for (String param : params) {
                    param = param.trim();
                    String[] parts = param.split("\\s+");
                    if (parts.length >= 2) {
                        Parameter p = new Parameter();
                        p.type = parts[parts.length - 2];
                        p.name = parts[parts.length - 1];
                        method.parameters.add(p);
                    }
                }
            }
        }
        
        return method;
    }

    private String generateMethodSummary(MethodInfo method) {
        String name = method.name;
        
        // Common method patterns
        if (name.startsWith("get")) {
            return "Gets the " + camelToWords(name.substring(3)) + ".";
        } else if (name.startsWith("set")) {
            return "Sets the " + camelToWords(name.substring(3)) + ".";
        } else if (name.startsWith("is")) {
            return "Checks if " + camelToWords(name.substring(2)) + ".";
        } else if (name.startsWith("has")) {
            return "Checks if has " + camelToWords(name.substring(3)) + ".";
        } else if (name.equals("toString")) {
            return "Returns a string representation of this object.";
        } else if (name.equals("equals")) {
            return "Checks if this object is equal to another object.";
        } else if (name.equals("hashCode")) {
            return "Returns the hash code value for this object.";
        } else {
            return camelToWords(name) + ".";
        }
    }

    private String generateParamDescription(Parameter param) {
        String name = param.name.toLowerCase();
        String type = param.type;
        
        // Common parameter patterns
        if (name.contains("name")) {
            return "the name to set";
        } else if (name.contains("id")) {
            return "the unique identifier";
        } else if (name.contains("index")) {
            return "the index position";
        } else if (name.contains("value")) {
            return "the value to set";
        } else if (type.equals("String")) {
            return "the " + name + " string";
        } else if (type.equals("int") || type.equals("Integer")) {
            return "the " + name + " number";
        } else if (type.equals("boolean") || type.equals("Boolean")) {
            return "true if " + name + ", false otherwise";
        } else {
            return "the " + name;
        }
    }

    private String generateReturnDescription(String returnType) {
        switch (returnType) {
            case "boolean":
            case "Boolean":
                return "true if successful, false otherwise";
            case "int":
            case "Integer":
                return "the result value";
            case "String":
                return "the result string";
            case "void":
                return "";
            default:
                return "the " + returnType + " instance";
        }
    }

    private String generateFieldDescription(String fieldName, String fieldType) {
        String name = fieldName.toLowerCase();
        
        if (name.contains("count") || name.contains("size")) {
            return "The number of elements.";
        } else if (name.contains("name")) {
            return "The name identifier.";
        } else if (name.contains("id")) {
            return "The unique identifier.";
        } else if (fieldType.equals("boolean") || fieldType.equals("Boolean")) {
            return "Flag indicating if " + camelToWords(fieldName) + ".";
        } else {
            return "The " + camelToWords(fieldName) + ".";
        }
    }

    private String extractExceptions(String line) {
        Pattern pattern = Pattern.compile("throws\\s+(.+?)\\s*\\{");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : "";
    }

    private String camelToWords(String camel) {
        if (camel == null || camel.isEmpty()) return "";
        
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(camel.charAt(0)));
        
        for (int i = 1; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append(" ").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }

    private String getIndentation(String line) {
        int i = 0;
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
            i++;
        }
        return line.substring(0, i);
    }

    // Helper classes
    private static class MethodInfo {
        String name = "";
        String returnType = "void";
        List<Parameter> parameters = new ArrayList<>();
    }

    private static class Parameter {
        String type;
        String name;
    }
}