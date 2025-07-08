# JavaDoc Generator++ 📝

![Java](https://img.shields.io/badge/Java-8%2B-007396?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0-blue.svg?style=flat-square)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Mac%20%7C%20Linux-lightgrey.svg?style=flat-square)

A command-line tool that automatically adds intelligent JavaDoc comments to your Java source files.

**Author:** Jin Park  
**Version:** 1.0  
**License:** MIT

## Features ✨

- **Automatic JavaDoc Generation**: Scans Java files and adds appropriate documentation
- **Smart Detection**: Identifies classes, interfaces, methods, and fields that need documentation
- **Intelligent Templates**: Generates context-aware descriptions based on naming patterns
- **Backup Creation**: Automatically creates `.backup` files before modification
- **Batch Processing**: Process entire directories recursively
- **Progress Tracking**: Shows real-time progress and statistics

## Requirements 📋

- Java 8 or higher
- Command line access (Terminal, Command Prompt, or PowerShell)
- Write permissions in target directories

## Installation 🔧

1. Clone or download this repository:
   ```bash
   git clone https://github.com/jinpark/javadoc-generator.git
   cd javadoc-generator
   ```

2. Compile the JavaDoc Generator:
   ```bash
   javac JavaDocGenerator.java
   ```

3. (Optional) Make the run script executable:
   ```bash
   chmod +x run.sh  # For Unix/Mac/Linux
   ```

## How to Use 🚀

### 1. Compile the JavaDoc Generator
```bash
javac JavaDocGenerator.java
```

### 2. Run on a Single Directory
```bash
java JavaDocGenerator ./src
```

### 3. Run on Current Directory
```bash
java JavaDocGenerator .
```

## Demo 🎬

```
$ java JavaDocGenerator ./src
🔍 Scanning directory: ./src
================================
📄 Processing: Employee.java
   ✓ JavaDocs added to Employee.java
📄 Processing: Manager.java
   ✓ JavaDocs added to Manager.java
📄 Processing: Department.java
   - Already documented or no changes needed
================================
✅ Processing complete!
📊 Files processed: 3
📝 JavaDocs added: 47
```

## Example Output

### Before (Employee.java):
```java
public class Employee {
    private String name;
    
    public String getName() {
        return name;
    }
}
```

### After (Employee.java):
```java
/**
 * Employee class.
 * TODO: Add detailed description
 * 
 * @author Jin Park
 * @version 1.0
 */
public class Employee {
    /**
     * The name identifier.
     */
    private String name;
    
    /**
     * Gets the name.
     * @return the result string
     */
    public String getName() {
        return name;
    }
}
```

## Generated JavaDoc Types 📋

### Classes
- Includes class description placeholder
- Adds @author and @version tags

### Methods
- Smart method summaries (detects getters, setters, boolean checks)
- @param tags for all parameters with intelligent descriptions
- @return tags for non-void methods
- @throws tags for declared exceptions

### Fields
- Context-aware field descriptions
- Recognizes common patterns (id, name, count, flags)

## Smart Pattern Recognition 🧠

The tool recognizes common patterns:
- **Getters**: `getName()` → "Gets the name."
- **Setters**: `setName()` → "Sets the name."
- **Boolean methods**: `isActive()` → "Checks if active."
- **Standard methods**: `toString()`, `equals()`, `hashCode()`

## Project Structure 📁

```
javadoc-generator/
├── JavaDocGenerator.java    # Main application class
├── Employee.java           # Sample file for testing
├── README.md              # Project documentation (this file)
├── .gitignore            # Git ignore file
├── run.sh               # Unix/Mac/Linux run script
├── run.bat             # Windows batch run script
└── examples/           # Example files (optional)
    ├── before/         # Original Java files
    └── after/          # Documented Java files
```

## Customization 🎨

To customize the JavaDoc templates, modify these methods in `JavaDocGenerator.java`:

1. **Change Default Author**: 
   ```java
   // Line 147 - Change from:
   doc.append(indentation).append(" * @author Jin Park\n");
   // To:
   doc.append(indentation).append(" * @author Your Name\n");
   ```

2. **Modify Templates**:
   - `generateMethodSummary()` - Customize method descriptions
   - `generateParamDescription()` - Customize parameter descriptions
   - `generateFieldDescription()` - Customize field descriptions

3. **Add Custom Patterns**:
   - Update `needsJavaDoc()` to detect additional code patterns
   - Add new cases in `generateJavaDoc()` for custom elements

## Testing the Tool 🧪

1. Save the sample `Employee.java` file
2. Compile and run the generator:
   ```bash
   javac JavaDocGenerator.java
   java JavaDocGenerator .
   ```
3. Check the modified `Employee.java` file
4. Original file is saved as `Employee.java.backup`

## Extending the Tool 🔧

TODO:
1. **Custom Templates**: Add configuration file for custom JavaDoc templates
2. **Annotation Support**: Detect and document custom annotations
3. **Multi-language**: Support for other JVM languages (Kotlin, Scala)
4. **IDE Integration**: Create plugins for popular IDEs
5. **Git Integration**: Auto-commit documented files
6. **Report Generation**: Create HTML reports of documentation coverage

## Technical Implementation 🛠️

### Core Technologies
- **Java I/O**: Uses `java.nio.file` for modern file handling
- **Regular Expressions**: Pattern matching for Java syntax recognition
- **String Manipulation**: Intelligent parsing and generation
- **Object-Oriented Design**: Clean separation of concerns

### Key Algorithms
1. **Pattern Detection**: Uses regex to identify Java elements
2. **Context Analysis**: Examines method/field names for intelligent descriptions
3. **Indentation Preservation**: Maintains original code formatting
4. **Safe File Handling**: Creates backups before modification

### Design Patterns Used
- **Builder Pattern**: For constructing JavaDoc comments
- **Strategy Pattern**: Different generation strategies for different elements
- **Template Method**: Base JavaDoc generation with specializations

## Common Issues & Solutions 🔍

### Issue: "Permission denied" error
**Solution**: Ensure you have write permissions in the target directory
```bash
chmod +w target_directory
```

### Issue: JavaDocs added to wrong places
**Solution**: The regex patterns might need adjustment for your coding style. Check the `needsJavaDoc()` method.

### Issue: Existing JavaDocs are duplicated
**Solution**: The tool checks for existing `*/` on the previous line. Ensure your existing JavaDocs follow standard format.

### Issue: "Could not find or load main class"
**Solution**: Make sure you're in the correct directory and the file compiled successfully:
```bash
ls JavaDocGenerator.class  # Should show the compiled file
```

## Future Enhancements 🚀

- [ ] Add support for enum documentation
- [ ] Generate method implementation TODOs
- [ ] Add copyright headers to files
- [ ] Support for generic type parameters
- [ ] Integration with popular build tools (Maven, Gradle)
- [ ] Documentation coverage reports

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License 📄

This project is open source. Feel free to modify and use in your projects!

## Author ✍️

**Jin Park**  
- GitHub: [@jinpark](https://github.com/JinParkmida)

---

Made with ❤️ by Jin Park | © 2025 JavaDoc Generator++
