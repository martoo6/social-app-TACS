package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbVerify;

/**
 * Created by martin on 4/27/15.
 */

public interface FacebookConnectorInterface {
    ListenableFuture<String> getLongLivedToken(String shortLivedToken);
    ListenableFuture<FbVerify> verifyAccessToken(String token);
    ListenableFuture<FbFriends> getUserFriends(String token);
    ListenableFuture<String> publishToWall(String token,String message);
    ListenableFuture<String> publishNotification(String token,String message);
    ListenableFuture<String> publishNotification(String token,String otherUserId,String message);
}
