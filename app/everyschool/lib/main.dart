import 'package:everyschool/api/firebase_api.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/page/home/home_page.dart';
import 'package:everyschool/page/main/bottom_navigation.dart';
import 'package:everyschool/page/main/splash.dart';
import 'package:everyschool/page/community/community_page.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
// import 'package:flutter/services.dart';
import 'package:intl/date_symbol_data_local.dart';
// fcm
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';

@pragma('vm:entry-point')
Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  await Firebase.initializeApp();
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

  SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(
    statusBarColor: Colors.transparent,
  ));
  await initializeDateFormatting();
  runApp(MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(fontFamily: "Pretendard"),
      home: Splash()));
}

class Main extends StatefulWidget {
  const Main({super.key});

  @override
  State<Main> createState() => _MainState();
}

class _MainState extends State<Main> {
  String? fcmToken;
  @override
  void initState() {
    super.initState();
    FirebaseApi().getMyDeviceToken();
    FirebaseApi().setupInteractedMessage(context);
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      FirebaseApi().foregroundMessage(message);
    });
    FirebaseApi().initializeNotifications(context);
  }

  int selectedIndex = 0;
  void onItemTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
  }

  final List<Widget> pages = [
    HomePage(),
    ConsultingListPage(),
    Center(child: Text('채팅')),
    CommunityPage(),
    Center(child: Text('전체보기')),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: pages[selectedIndex],
      bottomNavigationBar: SizedBox(
          height: 70,
          child:
              BtmNav(selectedIndex: selectedIndex, onItemTapped: onItemTapped)),
    );
  }
}
