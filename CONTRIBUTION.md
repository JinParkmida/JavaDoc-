# Contributing to JavaDoc Generator++

First off, thank you for considering contributing to JavaDoc Generator++! It's people like you that make JavaDoc Generator++ such a great tool.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title**
* **Describe the exact steps which reproduce the problem**
* **Provide specific examples to demonstrate the steps**
* **Describe the behavior you observed after following the steps**
* **Explain which behavior you expected to see instead and why**
* **Include details about your configuration and environment**

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a step-by-step description of the suggested enhancement**
* **Provide specific examples to demonstrate the steps**
* **Describe the current behavior and explain which behavior you expected to see instead**
* **Explain why this enhancement would be useful**

### Pull Requests

* Fill in the required template
* Do not include issue numbers in the PR title
* Follow the Java style guide
* Include thoughtfully-worded, well-structured tests
* Document new code with JavaDoc comments
* End all files with a newline

## Development Process

1. Fork the repo and create your branch from `main`
2. Make your changes and add tests if needed
3. Ensure the code compiles and runs correctly
4. Make sure your code follows the existing style
5. Issue that pull request!

## Java Style Guide

* Use 4 spaces for indentation (no tabs)
* Place opening braces on the same line
* Use meaningful variable and method names
* Add JavaDoc comments for all public methods
* Keep methods focused and small
* Follow standard Java naming conventions

## Testing Your Changes

Before submitting a pull request:

```bash
# Compile the project
javac JavaDocGenerator.java

# Test on sample files
java JavaDocGenerator ./test-files/

# Verify backups are created
ls *.backup

# Check the generated JavaDocs
diff Employee.java Employee.java.backup
```

## Attribution

This Contributing guide is adapted from the open-source contribution guidelines template.

Thank you for contributing to JavaDoc Generator++! 🎉