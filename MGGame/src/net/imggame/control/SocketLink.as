package net.imggame.control
{
	import flash.net.Socket;
    import flash.display.Sprite;
    import flash.events.*;
    import flash.text.TextField;
    
    public class SocketLink extends Sprite
    {
        private var socket:Socket;
        private var obj:Object;
        private var sessions:String;
        private var infoTxt:TextField;
        private var requestObj:Object;
        
        public function SocketLink(url:String,port:int):void
        {
            socket = new Socket();
            socket.addEventListener(ProgressEvent.SOCKET_DATA, socketDataHandler);
            socket.addEventListener(Event.CONNECT, connectHandler);
            socket.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            socket.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            socket.addEventListener(Event.CLOSE, closeHandler);
            socket.connect(url,port);
        }
        
        public function init():void
        {
            var temp = this.parent;
            infoTxt = temp.infoTxt;
        }
        
        //接收服务器数据
        private function socketDataHandler(event:ProgressEvent):void {
            infoTxt.appendText("接收数据\n");
            obj = new Object();
            obj=socket.readObject();
            for(var k in obj)
            {
                trace(k+":"+obj[k]);
            }
            trace("----------------------------------");
            recievedData();
        }
        
        //测试接收到的数据
        private function recievedData()
        {
            switch(obj.event)
            {
                case "checkMsg":
                infoTxt.appendText("第一次接收到的数据为"+obj.checkRuesult+","+obj.session+"\n");
                if(obj.checkRuesult)
                {
                    infoTxt.appendText("登陆成功"+"\n");
                    sessions = obj.session;
                    RequestFun();
                }
                else
                {
                    infoTxt.appendText("登陆失败"+"\n");
                }
                break;
                
                case "roleInit":
                infoTxt.appendText("第二次接收到的数据为"+obj.session+","+obj.roleName+","+obj.sceneInfo+","+obj.roleLocation+"\n");
                infoTxt.appendText("当前的session为："+obj.session+",将人物"+obj.roleName+"移动到地图"+obj.sceneInfo +"的"+obj.roleLocation+"的位置"+"\n"); 
                break;
            }
        }
        
        private function RequestFun():void
        {
            requestObj = new Object();
            requestObj = {event:"requestRoleInit",session:sessions,userName:"zhangfan",requestMsg:"roleInit"};
            socket.writeObject(requestObj);
            socket.flush();
        }

        //Socket已连接，发送数据
        private function connectHandler(event:Event):void {
            infoTxt.appendText("连接成功"+"\n");
            requestObj = new Object();
            requestObj = {event:"cookie",cookie:"cookieMsg"};
            socket.writeObject(requestObj);
            socket.flush();
        }
        
        //错误处理
        private function ioErrorHandler(event:IOErrorEvent):void {
            infoTxt.appendText("ioErrorHandler信息： " + event+"\n");
        }

        //安全问题处理
        private function securityErrorHandler(event:SecurityErrorEvent):void {
            infoTxt.appendText("securityErrorHandler信息: " + event+"\n");
        }
        
        //关闭Socket连接
        private function closeHandler(event:Event):void {
            infoTxt.appendText("连接关闭"+"\n");
        }
    }
}