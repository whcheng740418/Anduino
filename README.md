# RXTX_Tool


###功能:
```
1. 顯示目前PORT NAME
2. 選擇連線方式
3. 顯示接收資訊
```

###執行畫面
![alt tag](https://github.com/whcheng740418/RXTX_Tool/blob/master/imgs/Tool.png?raw=true)


###注意事項
```
sudo apt-get install librxtx-java
chmod o+rw /dev/ttyACM0
-Djava.library.path="/usr/lib/jni/" 
```

###察看com port資訊
```
sudo apt-get install setserial
setserial -a /dev/ttyACM0
```
