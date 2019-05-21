import 'dart:async';

import 'package:flutter/services.dart';

class FlutterGopayMidtrans {
  static const MethodChannel _channel =
      const MethodChannel('flutter_gopay_midtrans');

  static Future<String> configure({
    String client_id,
    String amount,
    String deadline,
    String gopaytoken
  }) async {
    assert(client_id != null);

    final String result = await _channel.invokeMethod('configGopay', {
      "client_id": client_id,
      "amount": amount,
      "deadline": deadline,
      "gopaytoken": gopaytoken
    });

    if(result != ""){
      throw Exception(result);
    }

    return result;
  }
}
