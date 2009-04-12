package net.imggame.business
{
	public final class XmlSendMessage extends SocketSendMessage
	{
		private SerializeSendMessage instance = null;
		private Socket socket;
		private function XmlSendMessage()
		{
		}
		
		public static function getInstence():XmlSendMessage
		{
			if(instance == null) {   
          		instance = new ClassicSingleton();   
        	}   
       		return instance;
		} 
		
		public static function sendMessage(str:String,sock:Socket):XmlSendMessage
		{
			this.socket = sock;
            this.socket.writeUTFBytes(str);
            this.socket.flush();
		}

	}
}