# windows下安装nvm，node版本管理工具



## 一、下载并安装nvm安装包

[nvm下载地址](https://github.com/coreybutler/nvm-windows)



## 二、配置下载源

在nvm安装目录下找到settings.txt文件

将下面代码粘贴到settings.txt文件底部

```
node_mirror:https://npm.taobao.org/mirrors/node/
npm_mirror:https://npm.taobao.org/mirrors/npm/
```



## 三、使用命令查看可安装node版本

### 	

1. 打开cmd
2. 输入nvm list available 查看可安装版本
3. 输入“nvm install 版本号”来安装对应版本的nodejs
4. 输入nvm ls查看已安装的node版本
5. 使用“nvm use 版本号”来切换nodejs版本
6. 使用node -v查看当前nodejs版本

