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
  - [x] Put JMenu items in new package and make each menu its own class
    - [x] Move menuBar definition to new class in menu package
  - [x] Create package for dialogs
  - [x] Make each area of the ui its own class
- [x] Implement file save & open functionality
  - [x] Create some sort of document class to hold EditorPane contents
  - [x] File save functionality
    - [x] Add item to *File* menu
    - [x] Create file picker dialog
    - [x] Make dialog functional
    - [x] Add checks to see if file already exists
  - [x] File open functionality
    - [x] Add item to *File* menu
    - [x] create file picker dialog
    - [x] Make dialog functional
    - [x] add safety checks for unsaved changes in currently open document
  - [x] Add safety checks to program to prompt saving unsaved changes
  - [x] refactor unsaved changes prompts to single function call
- [ ] Add general file information toolbar to bottom of window
  - [ ] Show file encoding
  - [ ] Line separator currently being used
  - [ ] Cursor position
- [ ] Implement a settings configurator
  - [ ] Add a way save settings to a file so that changes persist over program restarts
  - [ ] Create a settings dialog
  - [ ] Add a *Settings* item to a menu
- [ ] Add hotkey functionality
  - [ ] Load hotkeys from settings at program startup
  - [ ] Make menu item reflect hotkey
  - [ ] Add to settings dialog so that hotkeys can be configured
- [ ] Auto save functionality
- [ ] **Frequency analysis of characters in text**
- [ ] Word count feature
- [ ] Implement word wrapping
- [ ] Toggleable line numbering on the left side
- [ ] **File encryption with selectable encryption algorithm.**
- [ ] Highlighting for specified keywords
- [ ] Search functionality
