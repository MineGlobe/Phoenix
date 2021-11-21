# Phoenix

### Installation
1. Retrieve the jar from releases or compile the plugin via git and maven.
2. Add the project as a maven dependency or put it in your build path.
3. Start using the API!

### Usage

```java
private Phoenix phoenix

@Override
public void onEnable() {
    phoenix = new Phoenix(this);
}

```

### Example Usage

```java

import me.blazingtide.phoenix.Menu;

class ExampleMenu extends Menu {
}

```

```java
public void draw() {
    populator()
        .slot(slot)
        .item(new ItemStack(Material.DIAMOND_SWORD))
        .create()
}
```
