# Graph Visualization Java

A Java-based graph visualization application with an intuitive GUI that allows users to load, visualize, and analyze graphs using various layout algorithms.

## Overview

This project provides a comprehensive solution for graph visualization and analysis. It features:
- **Interactive GUI** built with Swing
- **Multiple Graph Layout Algorithms** (implemented in C++ backend)
- **Real-time Visualization** with customizable node and edge colors
- **Graph Input Support** from text files
- **Two Interaction Modes**: Active (interactive mode) and Passive (view-only mode)

## Project Structure

```
graph_visualization_java/
├── src/
│   ├── Main.java                    # Application entry point
│   ├── gui/
│   │   ├── MainWindow.java          # Main application window
│   │   ├── GraphCanvas.java         # Canvas for rendering graphs
│   │   └── ToolbarPanel.java        # Toolbar with controls
│   ├── model/
│   │   ├── Graph.java               # Graph data structure
│   │   ├── Node.java                # Node model
│   │   └── Edge.java                # Edge model
│   ├── utils/
│   │   └── GraphReader.java         # Graph file parser
│   └── integration/
│       └── IntegrationManager.java   # Backend integration
├── bin/                             # Compiled classes
├── engine/
│   └── graphProcessor.exe           # C++ graph processing backend
├── grid_graph.txt                   # Sample graph input
├── output.txt                       # Application output
└── README.md                        # This file
```

## Features

### Graph Visualization
- Load graphs from text files (format: `node1 node2 weight`)
- Automatic node positioning using layout algorithms
- Visual representation with customizable colors
- Zoom and pan capabilities

### Layout Algorithms
- Multiple graph positioning algorithms powered by C++ backend
- Supported modes:
  - **Active Mode**: Manual interaction with graph visualization
  - **Passive Mode**: View-only mode for presentations

### User Interface
- Clean white theme with modern design
- Center-aligned controls and buttons
- Intuitive toolbar with algorithm selection
- File browser for graph input
- Color picker for customization

## System Requirements

- **Java**: Java 8 or higher
- **Operating System**: Windows (graphProcessor.exe backend)
- **Runtime**: JVM with Swing support

## Building the Project

### Compile from Source
```bash
cd src
javac -d ../bin Main.java gui/*.java model/*.java utils/*.java integration/*.java
```

### Running the Application
```bash
cd bin
java Main
```

## Usage Guide

### 1. Load a Graph
- Click **"Pokaż nazwy węzłów"** (Show Node Names) to toggle node labels
- Click **"Pokaż wagi krawędzi"** (Show Edge Weights) to toggle edge weights
- Click **"Wczytaj krawędzię"** (Load Graph) to open a file browser
- Select a graph file (plain text format)

### 2. Choose Interaction Mode
- **Wczytaj wspólrzędne** (Load Coordinates): Passive mode - view pre-calculated positions
- **Tryb Pasywny** (Passive Mode): View-only visualization
- **Tryb Aktywny** (Active Mode): Interactive mode

### 3. Apply Layout Algorithm (Active Mode)
- Select an algorithm from the dropdown
- Click **"Wczytaj wspólrzędne"** to apply the algorithm
- The graph will be positioned according to the selected algorithm

### 4. Customize Appearance
- Click on **node color button** to change node colors
- Click on **edge color button** to change edge colors
- Toggle node names and edge weights as needed

## Input File Format

Graph files should be plain text with one edge per line:

```
node1 node2 weight
node1 node3 weight
node2 node3 weight
```

Example:
```
n_0_0 n_0_1 1.5
n_0_1 n_1_1 2.0
n_1_0 n_1_1 1.0
```

## Output Format

The application generates coordinate output in the following format:

```
node_name x_coordinate y_coordinate
n_0_0 914.949714 1000.000000
n_0_1 635.868814 1000.000000
n_1_0 1000.000000 1000.000000
```

Coordinates are normalized to range [0.0, 1000.0].

## Architecture

### Model Layer
- **Graph.java**: Core graph data structure with nodes and edges
- **Node.java**: Represents graph vertices with position and properties
- **Edge.java**: Represents graph connections with weights

### View Layer
- **MainWindow.java**: Main application frame and layout
- **GraphCanvas.java**: Custom Swing component for rendering
- **ToolbarPanel.java**: Control panel with buttons and selections

### Integration Layer
- **IntegrationManager.java**: Bridges Java GUI with C++ backend
- **GraphReader.java**: Parses input graph files

### Backend
- **graphProcessor.exe**: C++ executable handling graph layout algorithms

## Exception Handling

The application includes comprehensive exception handling for:
- File I/O operations with proper resource management
- Invalid graph data and missing nodes/edges
- Algorithm execution failures
- UI event handling and rendering errors
- Invalid user input validation

## Error Recovery

- Graceful error messages in dialog boxes
- Automatic state reset on critical errors
- Proper cleanup of resources on failure
- Detailed error logging to console