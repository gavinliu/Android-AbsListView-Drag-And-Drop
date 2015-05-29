## Android-AbsListView-Drag-And-Drop

A complex ListView & GridView with Drag and Drop functionality.

* Drag a item to drop menu view, call back drop function.

* Swipe choose item

## Usage

### Multi Choose

#### SelectionMode

1. SelectionMode.Official

It is ``AbsListView.setChoiceMode(CHOICE_MODE_MULTIPLE_MODAL)``, you also need ``setMultiChoiceModeListener()``.

```java
listView.setSelectionMode(SelectionMode.Official);

listView.setMultiChoiceModeListener(multiChoiceModeListener);
```

2. SelectionMode.Custom

It is a custom multi choose mode, you should use ``DragDropAttacher``.

```java
listView.setSelectionMode(SelectionMode.Custom);

listView.setDragDropAttacher(new DragDropAttacher(new DefaultHeaderTransformer(this), new DefaultFooterTransformer(this)));
```

#### Swipe choose

if SwipeChoise is true, the drag animation will invalid.

```java
listView.setIsSwipeChoise(true);
```

### DDListView


### DDGridView


## License

The MIT License (MIT)

Copyright (c) 2015 Liu Yunlong(gavin6liu@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
