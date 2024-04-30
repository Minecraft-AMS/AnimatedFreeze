# AnimatedFreeze - 动画冻结

**中文**| [**English**](README_en.md)

## 简介
- 该模组可以使用指令禁用任何材质的动画效果。

- 包含了[FastChest](https://github.com/FakeDomi/FastChest)模组的功能，您只需要启用内置的材质包并将其放在第一位即可。

- 如有遗漏的部分欢迎提交issue。

- 可能和部分资源包不兼容且关于这方面的问题将不会被处理。

## 指令
- **/animatedFreeze add <材质名>**
  
  添加一个需要禁用动画的材质。

  > 指令建议的值不是必须的，您可以自由的写入正确的材质名即使它不存在于指令所建议的值中。
  
- **/animatedFreeze remove <材质名>**

  移除一个已被禁用动画的材质。

- **/animatedFreeze removeAll**

  解禁所有材质。

- **/animatedFreeze list**

  查看已禁用动画的材质列表。

## 材质包

- **af_mod/chest_optimization（箱子优化）**

  改变了箱子的渲染方式，大幅提升FPS（使用时请将该材质放在第一位）。

  该实现方式与 [FastChest](https://github.com/FakeDomi/FastChest) 模组基本相同，因此有关于此材质的问题可以参考 [FastChest README](https://github.com/FakeDomi/FastChest/blob/master/README.md)。
