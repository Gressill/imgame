package net.imggame.business
{
	import flash.net.Socket;
	
	public final class SerializeSendMessage extends SocketSendMessage// implements Mysocket
	{
		private SerializeSendMessage instance = null;
		private Socket socket;
		private function SerializeSendMessage()
		{
		}
		
		public static function getInstence():SerializeSendMessage
		{
			if(instance == null) {   
          		instance = new ClassicSingleton();   
        	}   
       		return instance;
		} 
		
		public static function sendMessage(obj:Object,sock:Socket):SerializeSendMessage
		{
			this.socket = sock;
            this.socket.writeObject(obj);
            this.socket.flush();
		}

	}
}