# EncryptDecryptSolution  
加密方式AES/CBC/PKCS5Padding

## EncryptGUI 资源文件加密图形化界面
修改AESFileUtil内的iv、passwordBytes  
依次点击 File ➡️ Project Structure ➡️ Artifacts ➡️ + Jar ➡️ From module ➡️ 设置MainClass找到EncryptGUI，Dir for Meta 目录src后面的删除  
配置完成后点击 Build ➡️ Build Artifacts

## android-aes-jni 用于生成解密so库

### android.mk  
  LOCAL_MODULE 为生成后的so库名字，一会儿会用到  
### main.c  
  MAX_LEN 设置的是最大解密大小 TARGET_CLASS 设置的是Android代码路径，会做校验
  配置AES_IV、AES_KEY与EncryptGUI的AESFileUtil中相同  
  
  通过终端输入ndk-build编译为so库 
  
## AESCryptor.java 用于加载so库  
  将这个文件拷贝到你的Android项目中，复制完整包名，写入android-aes-jni main.c TARGET_CLASS
  System.loadLibrary("你的so库名(lib后 .so前)");  
  调用crypt方法进行解密即可 

## 加密图形化界面使用方式  
1.快速启动

Windows 
运行 Windows.bat

MacOS 
打开终端 cd至本目录
chmod +x MacOS.sh
./MacOS.sh

2.手动启动
MacOS 终端/Windows CMD：java -jar Encrypt.jar (注意路径)

## 由于美国法律限制，基于你的jdk版本，你可能会遇到这个错误
java.security.InvalidKeyException: Illegal key size 
报错解决方案：

UnlimitedJCEPolicyJDK8目录下的两个文件

如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件
如果安装了JDK,还要将两个jar文件也放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件。

## 出于安全考虑，我强烈建议你进行加固处理，无论是so库还是jar包

### so库加固 
市面上有很多对so库进行加固的支持，有收费的也有免费的，请自行选择，当然你也可以联系我

### jar包加固
使用Allatori 参考链接https://blog.csdn.net/jianning0925/article/details/107708437
