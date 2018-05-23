# FrontCover
简单的封面模板化制作


没有上包名<br>
没有任何像样的规范<br>
就当是新手代码<br>
一个不会用github的萌新<br>
一个Java爱瞎写的萌新<br>
大佬们高抬贵手<br>

# 有关于封面制作器自定义功能的制作
插件分两部分<br>
所有插件文件必须放在plugings目录下<br>
一部分是xml配置文档<br>
配置文档的文件命名随意<br>
举个例子<br>

name:必填，配置名，这个会出现在主界面的列表里，并且不能与其他插件的命名重复<br>
plugings:可选，这个填写需要的插件<br>
一旦填写了就要将插件放在plugings下才能加载<br>
&lt;FrontCover name="TeamSII口袋直播封面制作" plugings="SIIKDPlugings"&gt;

number:必填，图片数量，当文件为这个数时载入这个模板<br>
backgroundPath:必填，打底的图片路径，导出图片按照这个大小来导出<br>
topImagePath:可选，盖在最上面的图片路径，不继承<br>
&lt;format number="2" backgroundPath="img/SIIlive.png" topImagePath="img/SIIlive.png" high="266" wide="430"&gt;

每个图片放置的位置<br>
x，y，high，wide都可以继承自format<br>
&lt;point x="31" y="43"/&gt;<br>
&lt;point x="545" y="61" high="258" wide="385"/&gt;<br>
&lt;point x="34" y="334" high="249" wide="436"/&gt;<br>

&lt;format number="3" backgroundPath="img/SIIlive.png" topImagePath="img/SIIlive.png"&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&lt;point x="31" y="43" high="266" wide="430"/&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&lt;point x="545" y="61" high="258" wide="385"/&gt;<br>
&nbsp;&nbsp;&nbsp;&nbsp;&lt;point x="34" y="334" high="249" wide="436"/&gt;<br>
&lt;/format&gt;<br>

另一部分是Java插件
java插件的类必须实现imgInterface.ImgPlugings的接口
在FrontCover注册插件之后就可以启用
具体例子会放在plugings目录
佛系更新
