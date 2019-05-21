package org.digidaw.flutter_gopay_midtrans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.midtrans.sdk.corekit.callback.TransactionCallback;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterGopayMidtransPlugin */
public class FlutterGopayMidtransPlugin implements MethodCallHandler {

  private Context context;
  private Activity activity;
  MidtransSDK midtransSDK;
  private Result result;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_gopay_midtrans");
    channel.setMethodCallHandler(new FlutterGopayMidtransPlugin());

    FlutterGopayMidtransPlugin plugin = new FlutterGopayMidtransPlugin();

    plugin.context = registrar.context();
    plugin.activity = registrar.activity();
    plugin.midtransSDK = MidtransSDK.getInstance();
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("configGopay")) {
      String client_id = call.argument("client_id");
      String amount = call.argument("amount");
      String deadline = call.argument("deadline");
      String gopaytoken = call.argument("gopaytoken");
      String merchantUrl = call.argument("merchantUrl");
      this.result = result;

      configGopay(client_id, amount, deadline, gopaytoken, merchantUrl);
    } else {
      result.notImplemented();
    }
  }

  private void configGopay(String client_id, String amount, String deadline, String gopaytoken, merchantUrl){
    SdkUIFlowBuilder.init()
            .setContext(this.context)
            .setClientKey(client_id)
            .setMerchantBaseUrl(merchantUrl)
            .setTransactionFinishedCallback(new TransactionFinishedCallback(){
              @Override
              public void onTransactionFinished(TransactionResult transactionResult) {
                System.out.print("mantab");
              }
            })
            .buildSDK();

    midtransSDK.paymentUsingGoPay(gopaytoken, new TransactionCallback() {
      @Override
      public void onSuccess(TransactionResponse transactionResponse) {
        String deeplinkUrl = transactionResponse.getDeeplinkUrl();
        String qrCodeUrl = transactionResponse.getQrCodeUrl();
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkUrl)));
      }

      @Override
      public void onFailure(TransactionResponse transactionResponse, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        result.error("Error: " + s, "", "");
      }

      @Override
      public void onError(Throwable throwable) {
        try{
          Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
          result.error("Error: " + throwable.getMessage(), "", "");
        }
        catch (Exception e){
          e.printStackTrace();
        }
      }
    });

    this.result.success("");
  }
}
