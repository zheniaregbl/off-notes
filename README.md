# Off notes

Off notes is a cross-platform note editor where notes are stored as regular **Markdown** files.

## Features

* 📝 Create and edit Markdown notes
* 📁 Store notes as `.md` files
* 💾 Local-first data storage
* 📡 Transfer notes between devices over a local Wi-Fi network
* 🔄 Cross-platform support

## Cross-Platform

Built with **Kotlin Multiplatform** and **Compose Multiplatform**.

Supported platforms:

* Android
* iOS
* Desktop (Windows, macOS, Linux)

## Note Storage

Each note is stored as a separate Markdown file:

```text
notes/
├── First note.md
├── Shopping list.md
└── Project ideas.md
```

Notes can be accessed directly through the file system without being tied exclusively to the application.

## Local Synchronization

The application will support transferring and synchronizing notes between devices over a **local Wi-Fi network**.

> Local data transfer is currently under development.

## Technologies

* Kotlin Multiplatform
* Compose Multiplatform
* Kotlin Coroutines & Flow
* Koin
* Markdown

## Status

🚧 Work in progress.
