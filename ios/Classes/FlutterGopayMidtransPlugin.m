#import "FlutterGopayMidtransPlugin.h"
#import <flutter_gopay_midtrans/flutter_gopay_midtrans-Swift.h>

@implementation FlutterGopayMidtransPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterGopayMidtransPlugin registerWithRegistrar:registrar];
}
@end
