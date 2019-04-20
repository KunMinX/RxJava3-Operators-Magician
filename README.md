![](https://upload-images.jianshu.io/upload_images/57036-550b81d8d77c687d.jpg)


# 你用不惯 RxJava，只因缺了这把钥匙

## 前言

本文最初是为部门内部培训而准备的，但经过一番调研发现，同事们用不惯 RxJava，并不是因为网上介绍 “怎么用” 的教程不够多，恰恰是因为，一上来就急着发车的教程无数、却从未有过哪篇教程 **舍得用几句话的功夫点破 RxJava 操作符究竟为何方神圣**、我们为什么要用、为什么要那样用。

```java
Observable.just(1, 3, 5, 7, 9)
    .map(i -> i + 1)
    .filter(i -> i < 5)
    .subscribe(getObserve());
```

事实上，在相当长的一段时间里，我也和大部分人一样，只知道使用 RxJava 来完成异步回调，至于那些操作符，则是能不用尽量不用，因为不知道葫芦里卖的什么药，看不懂、不会用。

![RxJava三连](https://upload-images.jianshu.io/upload_images/57036-a20c7618a0416530.png)

因此，本文的初衷绝不是翻译官方文档、教大家怎么用，而是旨在帮助大家对 RxJava 操作符 **完成感性上的认识**。鉴于本次培训的效果还不错、同事们听了都说好，我便在 GitHub 开源了全套操作符示例代码（不要慌，链接文末已给出）。如果你在阅读本文后觉得恍然大悟，原来 RxJava 操作符是这么回事，那么我的愿望也就达到了。

![然而我并不发车](https://upload-images.jianshu.io/upload_images/57036-4a1585f97c9876a4.png)


## 编程语言包含多种编程范式

我对操作符本质的顿悟，始于我对编程语言的理解。和你一样，我是做安卓开发，但有一天，我决定跳出 Java，从整个计算机科学的角度来学习和理解编程语言的本质，在这过程中，我接触了“编程范式”这个概念，了解到原来每个编程语言大都包含多种编程范式。

常见的编程范式有：命令式编程、声明式编程等。

![编程范式](https://upload-images.jianshu.io/upload_images/57036-d09589d947e65251.png)

以 Java 为例，咱们 Java 最主要的编程范式是命令式编程。命令式编程 即按顺序执行具体的命令，这些命令**不仅交待了做什么，还详细交待了每一步怎么做**。

SQL 也是种编程语言，是一种典型的声明式编程。声明式编程的特点是，**只交待做什么，但无须交待怎么做**。


## 操作符的本质是声明式编程

下面回到我们最初的问题上来。你之所以用不惯 RxJava 操作符，是因为你习惯性地使用命令式编程思维 来理解实际上是声明式编程的操作符。
学习操作符，就和你在大学里接受的 SQL 语句一样理所当然。

SQL 你是理解的，就是按一定的规则，向数据库中的数据声明你要做什么。
![SQL代码示例](https://upload-images.jianshu.io/upload_images/57036-da21d86723997dd8.png)

同理，RxJava 也是按一定的规则，向数据流声明你要做什么。

![操作符伪代码示例](https://upload-images.jianshu.io/upload_images/57036-4dd92d6d426660d5.png)


转换成代码，便成为以下这样。

```java
Observable.just(1, 3, 5, 7, 9)
    .map(i -> i + 1)
    .filter(i -> i < 5)
    .subscribe(getObserve());
```
这样说，你理解了吗？

![请点赞 ~](https://upload-images.jianshu.io/upload_images/57036-3e15111b4263be48.png)


![blank](https://upload-images.jianshu.io/upload_images/57036-c53194da0800af8a.png)

# Rx钥匙：本周读者留言回复

上周我在掘金发布了 Rx钥匙，没想到在评论区收到那么多留言。

有的读者因为豁然开朗而喜出望外，不吝留下夸张的赞美；也有读者觉得意犹未尽，表达了对见证更多的愿望。

![](https://upload-images.jianshu.io/upload_images/57036-0c2e1cc89253dc22.png)

![](https://upload-images.jianshu.io/upload_images/57036-ed92fc17cc7284e6.png)

![](https://upload-images.jianshu.io/upload_images/57036-f960ade1565fa041.png)

从留言中我看到了大家对 Rx钥匙的期待 和 各自多彩的内心世界，这些期待甚至远远超出了 Rx钥匙本身，期望它是一把能够解决更多、带给自己更多的 魔法棒。

如你所愿，我在 GitHub 开源了这套操作符示例项目，该项目的计划分三步走：

1.收集业内关于操作符的 代码案例 和 最佳实践，以便随时查阅。

2.设计一款操作符组合工具，让人们可以直观而简便地组合操作符。

3.在组合工具的基础上映射代码，让代码可以自由复制。

![](https://upload-images.jianshu.io/upload_images/57036-07df9019c086acd0.png)

我会不定期地只在公众号公布 Rx钥匙 研发的最新动态，并固定在每周三或周四下午 2点在公众号或掘金推送新的短平快文章。

公众号刚开通，文章留言功能暂无法使用，欢迎在公众号中与 KunMinX（可以叫我坤哥）交流，消息看到了我便回复。



我的微信公众号

![公众号](https://upload-images.jianshu.io/upload_images/57036-dc3af94a5daf478c.jpg)

