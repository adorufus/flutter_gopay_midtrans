import Flutter
import UIKit

public class SwiftFlutterGopayMidtransPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_gopay_midtrans", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterGopayMidtransPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
