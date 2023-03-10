# Simple Text Editor

The goal of this project is to learn Java by creating a text editor with a Swing UI using Gradle as a build system.

## Task list

- [x] Simple window with text pane
- [x] Add menu toolbar to window
  - [x] Create menu toolbar
  - [x] Create *Help* menu
    - [x] Add *About* dialog
  - [x] Create *File* menu
    - [x] Add *New File* item
    - [x] Add *Exit* item
- [x] Restructure *app.ui* package so that MainWindow doesn't look like an endless list of declarations
  - [x] put JMenu items in new package and make each menu its own class
    - [x] move menuBar definition to new class in menu package
  - [x] create package for dialogs
  - [x] make each area of the ui its own class
- [ ] File save functionality
  - [ ] Create file picker dialog
  - [ ] Add item to *File* menu
  - [ ] Add hotkey functionality
  - [ ] Make menu item reflect hotkey
- [ ] File open functionality
    - [ ] Reuse file picker dialog
    - [ ] Add item to *File* menu
    - [ ] Add hotkey functionality
    - [ ] Make menu item reflect hotkey
- [ ] Auto save functionality
- [ ] **Frequency analysis of characters in text**
- [ ] Word count feature
- [ ] Toggleable line numbering on the left side
- [ ] **File encryption with selectable encryption algorithm.**
- [ ] Highlighting for specified keywords
- [ ] Search functionality
