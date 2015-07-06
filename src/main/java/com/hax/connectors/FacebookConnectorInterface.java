package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbVerify;

/**
 * Created by martin on 4/27/15.
 */

public interface FacebookConnectorInterface {
    String getLongLivedToken(String shortLivedToken);
    FbVerify verifyAccessToken(String token);
    FbFriends getUserFriends(String token);
    String publishToWall(String token,String message);
    String publishNotification(String token,String message);
    String publishNotification(String token,String otherUserId,String message);
}
