# Visual Guide - Data Structures in Action

This guide provides visual representations of how each data structure works in the game.

---

## HashMap - Level Management

### Structure
```
┌────────────────────────────────────────────────────────┐
│              HashMap<String, LevelData>                │
├────────────────────────────────────────────────────────┤
│                                                        │
│  "Beach Scene"    →  [LevelData: beach1.jpg,          │
│                                   beach2.jpg,          │
│                                   5 differences]       │
│                                                        │
│  "Jungle Scene"   →  [LevelData: jungle1.jpg,         │
│                                   jungle2.jpg,         │
│                                   7 differences]       │
│                                                        │
│  "City Scene"     →  [LevelData: city1.jpg,           │
│                                   city2.jpg,           │
│                                   10 differences]      │
│                                                        │
│  "Space Scene"    →  [LevelData: space1.jpg,          │
│                                   space2.jpg,          │
│                                   12 differences]      │
│                                                        │
│  "Fantasy Scene"  →  [LevelData: fantasy1.jpg,        │
│                                   fantasy2.jpg,        │
│                                   15 differences]      │
│                                                        │
└────────────────────────────────────────────────────────┘

Operation: levels.get("Beach Scene")
Result:    O(1) instant retrieval of level data
```

---

## HashSet - Difference Checking

### Initial State (5 differences)
```
┌─────────────────────────────────────────┐
│    HashSet<Difference> (Size: 5)        │
├─────────────────────────────────────────┤
│  • Difference(150, 200, 30)             │
│  • Difference(300, 150, 25)             │
│  • Difference(450, 300, 30)             │
│  • Difference(200, 400, 25)             │
│  • Difference(500, 250, 30)             │
└─────────────────────────────────────────┘
```

### After Player Finds 2 Differences
```
Player clicks at (155, 205) → Within radius of (150, 200)
Player clicks at (305, 155) → Within radius of (300, 150)

┌─────────────────────────────────────────┐
│    HashSet<Difference> (Size: 3)        │
├─────────────────────────────────────────┤
│  • Difference(450, 300, 30)             │
│  • Difference(200, 400, 25)             │
│  • Difference(500, 250, 30)             │
└─────────────────────────────────────────┘

Operation: O(1) check and O(1) remove
```

### Level Complete
```
┌─────────────────────────────────────────┐
│    HashSet<Difference> (Size: 0)        │
├─────────────────────────────────────────┤
│  [EMPTY - LEVEL COMPLETE!]              │
└─────────────────────────────────────────┘
```

---

## Stack - Undo System (LIFO)

### Player Finds 3 Differences
```
Step 1: Find difference at (150, 200)
┌──────────────────────┐
│       Stack          │
├──────────────────────┤
│ (150, 200, 30)  ← TOP│
└──────────────────────┘

Step 2: Find difference at (300, 150)
┌──────────────────────┐
│       Stack          │
├──────────────────────┤
│ (300, 150, 25)  ← TOP│
│ (150, 200, 30)       │
└──────────────────────┘

Step 3: Find difference at (450, 300)
┌──────────────────────┐
│       Stack          │
├──────────────────────┤
│ (450, 300, 30)  ← TOP│
│ (300, 150, 25)       │
│ (150, 200, 30)       │
└──────────────────────┘
```

### Player Clicks Undo
```
Pop operation (LIFO - Last In, First Out)

┌──────────────────────┐
│       Stack          │
├──────────────────────┤
│ (300, 150, 25)  ← TOP│
│ (150, 200, 30)       │
└──────────────────────┘

Returned: (450, 300, 30)
Action: Add back to HashSet
```

---

## Queue - Hint System (FIFO)

### Initial Queue (5 hints available)
```
┌────────────────────────────────────────────────────┐
│                   Queue<Difference>                │
├────────────────────────────────────────────────────┤
│  FRONT → (150,200) → (300,150) → (450,300) →      │
│          (200,400) → (500,250) ← REAR              │
└────────────────────────────────────────────────────┘
```

### Player Requests Hint #1
```
Poll operation (FIFO - First In, First Out)

┌────────────────────────────────────────────────────┐
│                   Queue<Difference>                │
├────────────────────────────────────────────────────┤
│  FRONT → (300,150) → (450,300) → (200,400) →      │
│          (500,250) ← REAR                          │
└────────────────────────────────────────────────────┘

Returned: (150, 200, 30)
Action: Show hint at location (150, 200)
```

### Player Requests Hint #2
```
┌────────────────────────────────────────────────────┐
│                   Queue<Difference>                │
├────────────────────────────────────────────────────┤
│  FRONT → (450,300) → (200,400) → (500,250) ← REAR │
└────────────────────────────────────────────────────┘

Returned: (300, 150, 25)
Action: Show hint at location (300, 150)
```

---

## LinkedList - High Score Leaderboard

### Initial State (Empty)
```
┌──────────────────────────────────────┐
│   LinkedList<PlayerScore> (Empty)    │
├──────────────────────────────────────┤
│  HEAD → [null] ← TAIL                │
└──────────────────────────────────────┘
```

### After 3 Players
```
┌─────────────────────────────────────────────────────────┐
│          LinkedList<PlayerScore> (Size: 3)              │
├─────────────────────────────────────────────────────────┤
│  HEAD → [Alice: 850] → [Bob: 720] → [Carol: 650] ← TAIL│
└─────────────────────────────────────────────────────────┘

Sorted in descending order (highest first)
```

### New Score: Dave with 780 points
```
Traverse list to find insertion point:
1. 780 < 850 (Alice) → continue
2. 780 > 720 (Bob)   → INSERT HERE

┌──────────────────────────────────────────────────────────────┐
│          LinkedList<PlayerScore> (Size: 4)                   │
├──────────────────────────────────────────────────────────────┤
│  HEAD → [Alice: 850] → [Dave: 780] → [Bob: 720] →          │
│         [Carol: 650] ← TAIL                                  │
└──────────────────────────────────────────────────────────────┘

Operation: O(n) traversal + insertion to maintain sorted order
```

---

## Graph - Level Progression

### Graph Structure (Adjacency List)
```
┌─────────────────────────────────────────────────────────┐
│                  Level Progression Graph                │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Beach (UNLOCKED, INCOMPLETE)                           │
│    Adjacency List: [Jungle, City]                       │
│                                                         │
│  Jungle (LOCKED, INCOMPLETE)                            │
│    Adjacency List: [Space]                              │
│                                                         │
│  City (LOCKED, INCOMPLETE)                              │
│    Adjacency List: [Space]                              │
│                                                         │
│  Space (LOCKED, INCOMPLETE)                             │
│    Adjacency List: [Fantasy]                            │
│                                                         │
│  Fantasy (LOCKED, INCOMPLETE)                           │
│    Adjacency List: []                                   │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Visual Representation
```
         ┌──────────┐
         │  Beach   │ ⭐ UNLOCKED
         │ ★☆☆☆☆    │
         └────┬─────┘
              │
      ┌───────┴────────┐
      ▼                ▼
┌──────────┐     ┌──────────┐
│  Jungle  │     │   City   │ 🔒 LOCKED
│ ★★☆☆☆    │     │ ★★★☆☆    │
└────┬─────┘     └────┬─────┘
     │                │
     └───────┬────────┘
             ▼
       ┌──────────┐
       │  Space   │ 🔒 LOCKED
       │ ★★★★☆    │
       └────┬─────┘
            ▼
       ┌──────────┐
       │ Fantasy  │ 🔒 LOCKED
       │ ★★★★★    │
       └──────────┘
```

### After Completing Beach Level
```
Beach.markCompleted() → Unlocks adjacent levels

         ┌──────────┐
         │  Beach   │ ✅ COMPLETED
         │ ★☆☆☆☆    │
         └────┬─────┘
              │
      ┌───────┴────────┐
      ▼                ▼
┌──────────┐     ┌──────────┐
│  Jungle  │     │   City   │ ⭐ UNLOCKED
│ ★★☆☆☆    │     │ ★★★☆☆    │
└────┬─────┘     └────┬─────┘
     │                │
     └───────┬────────┘
             ▼
       ┌──────────┐
       │  Space   │ 🔒 LOCKED (requires Jungle OR City)
       │ ★★★★☆    │
       └────┬─────┘
            ▼
       ┌──────────┐
       │ Fantasy  │ 🔒 LOCKED
       │ ★★★★★    │
       └──────────┘
```

---

## Complete Game Flow with Data Structures

```
┌─────────────────────────────────────────────────────────┐
│                    GAME START                           │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
      ┌─────────────────────┐
      │   Main Menu (UI)    │
      └──────────┬──────────┘
                 │
      ┌──────────┴──────────┐
      │                     │
      ▼                     ▼
┌──────────┐        ┌──────────────┐
│High Score│        │Level Selection│
│(LinkedList)       │  (Graph)      │
└──────────┘        └──────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ Load Level      │
                  │ (HashMap.get)   │
                  └────────┬────────┘
                           │
                           ▼
            ┌──────────────────────────────┐
            │      GAMEPLAY LOOP           │
            ├──────────────────────────────┤
            │                              │
            │  ┌────────────────────┐      │
            │  │ Player Click       │      │
            │  │ ↓                  │      │
            │  │ HashSet.contains() │      │
            │  │ O(1) check         │      │
            │  └─────────┬──────────┘      │
            │            │                 │
            │     ┌──────┴──────┐          │
            │     │    FOUND?   │          │
            │     └─┬─────────┬─┘          │
            │       │ YES     │ NO         │
            │       ▼         ▼            │
            │  ┌────────┐  ┌──────┐       │
            │  │HashSet │  │Score │       │
            │  │.remove()│  │  -5  │       │
            │  │Stack   │  └──────┘       │
            │  │.push() │                 │
            │  │Score+100│                │
            │  └────────┘                 │
            │                              │
            │  ┌────────────────────┐      │
            │  │ Hint Button        │      │
            │  │ ↓                  │      │
            │  │ Queue.poll()       │      │
            │  │ FIFO hint          │      │
            │  └────────────────────┘      │
            │                              │
            │  ┌────────────────────┐      │
            │  │ Undo Button        │      │
            │  │ ↓                  │      │
            │  │ Stack.pop()        │      │
            │  │ LIFO reversal      │      │
            │  └────────────────────┘      │
            │                              │
            └──────────────┬───────────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ HashSet.isEmpty()│
                  │ Level Complete?  │
                  └────────┬─────────┘
                           │ YES
                           ▼
                  ┌─────────────────┐
                  │ Graph.complete() │
                  │ Unlock adjacent  │
                  └────────┬─────────┘
                           │
                           ▼
                  ┌─────────────────────┐
                  │ LinkedList.add()    │
                  │ Insert high score   │
                  │ (maintain sorted)   │
                  └─────────────────────┘
```

---

## Time Complexity Visualization

```
┌──────────────────────────────────────────────────────┐
│         Operation Performance Comparison             │
├──────────────────────────────────────────────────────┤
│                                                      │
│  HashMap.get("level")           O(1) ●              │
│  HashSet.contains(diff)         O(1) ●              │
│  Stack.push(diff)               O(1) ●              │
│  Stack.pop()                    O(1) ●              │
│  Queue.poll()                   O(1) ●              │
│  LinkedList.add(index, score)   O(n) ●────────────  │
│  Graph.addEdge(node)            O(1) ●              │
│  Graph.BFS(start)               O(V+E) ●──────────  │
│                                                      │
│  ● = Single operation                               │
│  ────── = Multiple operations (linear)              │
│                                                      │
└──────────────────────────────────────────────────────┘

Legend:
O(1)   = Constant time (instant)
O(n)   = Linear time (depends on size)
O(V+E) = Graph traversal (vertices + edges)
```

---

## Memory Layout

```
┌─────────────────────────────────────────────────────┐
│                  HEAP MEMORY                        │
├─────────────────────────────────────────────────────┤
│                                                     │
│  [HashMap] → Buckets → [LevelData objects]         │
│     ↓                                               │
│  ~5 entries × ~500 bytes = ~2.5 KB                 │
│                                                     │
│  [HashSet] → Buckets → [Difference objects]        │
│     ↓                                               │
│  ~15 max × ~50 bytes = ~750 bytes                  │
│                                                     │
│  [Stack] → Array → [Difference references]         │
│     ↓                                               │
│  ~5 max × ~50 bytes = ~250 bytes                   │
│                                                     │
│  [Queue] → LinkedList → [Difference references]    │
│     ↓                                               │
│  ~15 max × ~50 bytes = ~750 bytes                  │
│                                                     │
│  [LinkedList] → Nodes → [PlayerScore objects]      │
│     ↓                                               │
│  ~10 max × ~100 bytes = ~1 KB                      │
│                                                     │
│  [Graph] → HashMap → [LevelNode objects]           │
│     ↓                                               │
│  ~5 nodes × ~200 bytes = ~1 KB                     │
│                                                     │
│  Total Game Data: ~6 KB (very efficient!)          │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Summary: Why Each Data Structure?

| Data Structure | Use Case | Reason |
|----------------|----------|--------|
| **HashMap** | Level storage | Need fast lookup by name (O(1)) |
| **HashSet** | Active differences | Need fast check if clicked (O(1)) |
| **Stack** | Undo history | Need LIFO (most recent first) |
| **Queue** | Hint delivery | Need FIFO (fair distribution) |
| **LinkedList** | High scores | Need sorted insertion anywhere |
| **Graph** | Level unlocks | Need relationship modeling |

**Each structure was chosen for its unique strengths!**

---

**This visual guide helps you understand exactly how data structures power the game!**

