# EncryptDecryptSolution  
加密方式AES/CBC/PKCS5Padding

## EncryptGUI 资源文件加密图形化界面
修改AESFileUtil内的iv、passwordBytes  
依次点击 File ➡️ Project Structure ➡️ Artifacts ➡️ + Jar ➡️ From module ➡️ 设置MainClass找到EncryptGUI，Dir for Meta 目录src后面的删除  
配置完成后点击 Build ➡️ Build Artifacts

## android-aes-jni 用于生成解密so库
### android.mk  
  LOCAL_MODULE 为生成后的so库名字，一会儿会用到  
main.c  
  MAX_LEN 设置的是最大解密大小 TARGET_CLASS 设置的是Android代码路径，会做校验
  配置AES_IV、AES_KEY与EncryptGUI的AESFileUtil中相同  
  通过终端输入ndk-build编译为so库 
  
## AESCryptor.java 用于加载so库  
  将这个文件拷贝到你的Android项目中，复制完整包名，写入android-aes-jni main.c TARGET_CLASS
  System.loadLibrary("你的so库名(lib后 .so前)");  
  调用crypt方法进行解密即可 
