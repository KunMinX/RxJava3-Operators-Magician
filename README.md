![](https://upload-images.jianshu.io/upload_images/57036-a656f3cb7ab534cd.jpg)

# Rx钥匙：为无聊而生的 Android 开发者工具
`更新动态 | 2019.4.25`

很高兴又和大家见面！

有不少热心网友关心，上一期的 [《你用不惯 RxJava，只因缺了这把钥匙》](https://juejin.im/post/5cb82a42e51d456e62545ac6) 后来怎么样了，再次谢谢大家的期待，原本目标只是给大家一把钥匙，方便大家意会 RxJava 操作符的本质，想不到在后台收到 40 多条留言，且有不少网友对 Rx钥匙 的期待远远超出了其本身，期望那是一把能够解决无聊、带给自己更多的魔法棒。

![](https://upload-images.jianshu.io/upload_images/57036-d64787fa6df74e62.png)

再加上，我自己也因为 **“拿 SQL 来隐喻操作符”** 的那番见解，而心生灵感：

不如做一件史上最无聊的事吧 —— **像设计师一样将每个像素都抠到极致，像工程师一样将工程设计模式和原则应用到每一个功能的编写，用无聊的方式来对抗无聊** —— 这是一款为无聊而生的面向 Android 开发者的操作符练习工具。

| Add Operators | Output Code |  Clear Expressions |
| :-: | :-: | :-: |
|![](https://upload-images.jianshu.io/upload_images/57036-bcb97adb16f309ea.gif)|![](https://upload-images.jianshu.io/upload_images/57036-77454b962bfc9d09.gif) |![](https://upload-images.jianshu.io/upload_images/57036-4dfd67d168698644.gif) |

除了提供 “解决无聊” 这一最核心功能之外，你还可以从这个开源项目获得的内容包括：

1. 整洁的代码风格和标准的资源命名规范。
2. 基于前沿的、遵循关注点分离的 JetPack MVVM 架构。
3. 使用 RxJava 和 lambda 表达式。
4. AndroidX 和 Material Design 2 的全面使用。
5. ConstraintLayout 约束布局的最佳实践。
6. 为提升手机桌面的逼格做贡献，让知识与美观并存，即使从不打开。
7. 绝不使用 Dagger，绝不使用奇技淫巧、编写艰深晦涩的代码。
8. 长期更新。

如你所见，Rx钥匙的界面效果大体已经出来了，我的目标是，将首页先打磨好，这样 Rx钥匙 Apk 最快可能下周就能正式和大家见面了。

鉴于目前在 RxJava 表达式的书写上存有 “字符联想” 上的小问题，如果你对此十分熟悉，请不吝参与进来，这个项目会因为你的参与而变得更好！

# 下载体验

[![getFromGooglePlay](https://github.com/KunMinX/RxJava2-Operators-Sample/blob/master/img/google-play1.png)](https://www.coolapk.com/apk/227547)
 [![getFromCoolapk](https://github.com/KunMinX/RxJava2-Operators-Sample/blob/master/img/coolapk1.png)](https://www.coolapk.com/apk/227547)

# 往期热门动态

[2019.4.20：Rx钥匙：本周读者留言回复](https://github.com/KunMinX/RxJava2-Operators-Sample/blob/master/README_old_article.md#reply20190420)

[2019.4.18：你用不惯 RxJava，只因缺了这把钥匙 🔥](https://github.com/KunMinX/RxJava2-Operators-Sample/blob/master/README_old_article.md#key20190418)

# Thanks to

[LinkageRecyclerView](https://github.com/KunMinX/LinkageRecyclerView)

[RxJava2](https://github.com/ReactiveX/RxJava)

[RxJava2-Android-Samples](https://github.com/amitshekhariitbhu/RxJava2-Android-Samples)

[material-components-android](https://github.com/material-components/material-components-android)

[AndroidX](https://developer.android.google.cn/jetpack/androidx)

[CodeView](https://github.com/Thereisnospon/CodeView)


# My Pages

Email：[kunminx@gmail.com](mailto:kunminx@gmail.com)

Home：[KunMinX 的个人博客](https://kunminx.github.io/)

Juejin：[KunMinX 在掘金](https://juejin.im/user/58ab0de9ac502e006975d757/posts)

[《重学安卓》 专栏](https://xiaozhuanlan.com/kunminx?rel=kunminx)

[![重学安卓小专栏](https://i.loli.net/2019/06/17/5d067596c2dbf49609.png)](https://xiaozhuanlan.com/kunminx?rel=kunminx)

# 特别推荐

![shejizhilu.png](https://i.loli.net/2019/09/16/czf5obHZILSVmDn.png)

### [致 独立开发者] 推荐一个 科普 设计知识 的专栏

原本只是提个需求，没有抱太大期望，没想到很快就在评论区收到作者的答复，并在假期内特别准备了这篇文章，在此非常感谢作者对设计的热爱和付出！

本人从接触到从事 Android 开发已有 4 年之久，在经历过近 3 年的架构实践、琢磨 和 反复设计后，**标准化的开发模式已完全确立**。

现如今，我独立完成一款 29 个页面、34 个 API、350 余项 细节的项目，在自动化脚本的帮助下，只需令人咂舌的 N 天时间。

我承认比起 工程设计，我在 视觉设计 和 交互设计 上毫无天分。**我常常为 此处应摆放什么样的控件、控件应呈现什么样式 纠结不已**。

网上收集的飞机稿看似都可以，但我没有一个唯一正确的标准来指导、来相信、从而毫无疑问地做出正确的、自己能够笃信的 视觉设计。**因而只要谈到 产品设计，我随时处于崩溃的边缘**。

然而我没有告诉作者的是，作者在文中对 “为什么” 的阐述，让我重新燃起了学习设计的希望，因为 **原来设计也是可以通过思考掌握的，被设计的样式 绝非凭空存在，是有客观的依据和考虑在里边的**。

![](https://i.loli.net/2019/09/16/5vmsuwJjL3RnHYr.png)

![](https://i.loli.net/2019/09/16/vznLRw4UHdSitMZ.png)

最后，再次感谢作者的分享！

《设计知录》 https://xiaozhuanlan.com/ui-x?rel=kunminx


# License

```
Copyright 2018-2019 KunMinX

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
