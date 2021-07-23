# EncryptDecryptSolution  
客户端下载文件全流程加密解密方案，加密、解密、使用全流程不暴露密钥、原文，适用场景下载加密的动画文件后解密并在内存中播放，隔绝存储
 

本项目没有选择非对称加密算法，主要原因是解密时间较长，用在客户端实时使用不太合适，选择对称加密算法除了解密更快以外也是因为在难易度层面解决了密钥容易被破解的问题，当然归根结底这里提供的是整套的解决方案，使用什么算法是你的自由

## 一。android-aes-jni 用于生成解密so库

### android.mk  
  LOCAL_MODULE 为生成后的so库名字，一会儿会用到  
### main.c  
  MAX_LEN 设置的是最大解密大小 TARGET_CLASS 设置的是Android代码路径，会做校验
  配置AES_IV、AES_KEY与EncryptGUI的AESFileUtil中相同  
  
  通过终端输入ndk-build编译为so库 
  
## 二。AESCryptor.java 用于加载so库  
  将这个文件拷贝到你的Android项目中，复制完整包名，写入android-aes-jni main.c TARGET_CLASS
  System.loadLibrary("你的so库名(lib后 .so前)");  
  调用crypt方法进行解密即可 
  
## 三。EncryptGUI 文件加密图形化界面，额外支持，非必需
修改AESFileUtil内的iv、passwordBytes  
依次点击 File ➡️ Project Structure ➡️ Artifacts ➡️ + Jar ➡️ From module ➡️ 设置MainClass找到EncryptGUI，Dir for Meta 目录src后面的删除  
配置完成后点击 Build ➡️ Build Artifacts  

这里我建议你的jdk版本配置为1.8，如果你使用高版本jdk生成的jar包在别人的电脑上运行会存在兼容性的问题

## 四。加密图形化界面使用方式  
1.快速启动

Windows 
运行 Windows.bat

MacOS 
打开终端 cd至本目录
chmod +x MacOS.sh
./MacOS.sh

2.手动启动
MacOS 终端/Windows CMD：java -jar Encrypt.jar (注意路径)

### 由于美国法律限制，基于你的jdk版本，你可能会遇到这个错误
java.security.InvalidKeyException: Illegal key size 
报错解决方案：

UnlimitedJCEPolicyJDK8目录下的两个文件

如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件  
如果安装了JDK，还要将两个jar文件也放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件。

## 五。安全性

### so库加固 
我们可以通过IDA反编译so库拿到密钥，这是非常不安全的，我强烈建议你对so库进行加固处理 

市面上有很多对so库进行加固的支持，费用和安全性也不完全相同，请自行选择，当然你也可以联系我

### jar包加固
使用Allatori 参考链接https://blog.csdn.net/jianning0925/article/details/107708437

## 六。VAP场景支持 
我在VAP提交了代码，你可以使用StreamContainer传入解密出的byteArray直接进行播放，避免了解密到磁盘目录被他人盗用 
https://github.com/Tencent/vap/commit/2aee1bd21930f4ce0c9f0ecb56ad2645f392e38f  

## 七。鸣谢 
https://github.com/VincentJin-91  
https://github.com/panxw  
https://github.com/Tencent/vap  
