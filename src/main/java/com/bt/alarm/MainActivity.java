package com.bt.alarm;
 
import android.app.Activity;
import android.os.Bundle;
import android.content.IntentFilter;
import android.content.Intent;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.widget.Toast;
import android.media.RingtoneManager;
import android.media.Ringtone;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.transition.Visibility;

public class MainActivity extends Activity { 

public TextView txt;
public Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(BTReceiver, filter);

		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		
		txt = findViewById(R.id.txt);
    }
	
	//The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver BTReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
				//Do something if connected
				Toast.makeText(getApplicationContext(), "Bluetooth Connected", Toast.LENGTH_SHORT).show();
				TextCh(1);
			}
			else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
				//Do something if disconnected
				Toast.makeText(getApplicationContext(), "Bluetooth Disconnected", Toast.LENGTH_SHORT).show();
				TextCh(0);
				//Play alarm
				r.play();
			}
		}
	
};
	public void Stop(View view){
	    if(r.isPlaying()){ 
		r.stop();
		TextCh(2);
	    }
	}
	public void TextCh(int state){
	if(state == 1){ 
	txt.setText("Bluetooth device connected");
	    }
	else if(state == 0){ 
	txt.setText("Bluetooth device disconnected");
	    }
	else if(state == 2){
	txt.setText("Alarm Stopped!");
	    }
	}
}
