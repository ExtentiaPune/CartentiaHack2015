import android.content.Intent;

import com.extentia.cartentia.gcm.RegistrationIntentService;
import com.google.android.gms.iid.InstanceIDListenerService;

public class CartentiaInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "CartentiaInstanceIDListenerService";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}