import 'package:everyschool/api/firebase_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/main.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class LoginForm extends StatefulWidget {
  const LoginForm({super.key, this.emailAddress, this.password});

  final emailAddress;
  final password;

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final storage = FlutterSecureStorage();

  void loginSuccess() async {
    var token = await storage.read(key: 'token') ?? "";
    final userinfo = await UserApi().getUserInfo(token);
    await Provider.of<UserStore>(context, listen: false).setUserInfo(userinfo);
    print('여기는 스토아 ');
    print(context.read<UserStore>().userInfo);
    Navigator.of(context).pushReplacement(MaterialPageRoute(
      builder: (_) => Main(),
    ));
  }

  void _showLoginFailureDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('로그인 실패'),
        content: Text('이메일 또는 비밀번호가 올바르지 않습니다.'),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: Text('확인'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      child: Column(
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              controller: widget.emailAddress,
              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(
                    Icons.alternate_email_rounded,
                  ),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                  labelText: '이메일',
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.emailAddress,
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              controller: widget.password,

              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(Icons.vpn_key_outlined),
                  border: OutlineInputBorder(),
                  labelText: '비밀번호',
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.visiblePassword,
              obscureText: true, // 비밀번호 안보이도록 하는 것
            ),
          ),
          SizedBox(
            height: 60,
            child: Padding(
              padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
              child: ButtonTheme(
                  child: TextButton(
                      onPressed: () async {
                        final deviceToken =
                            await FirebaseApi().getMyDeviceToken();
                        int response = await UserApi().login(
                            widget.emailAddress, widget.password, deviceToken);
                        if (response == 1) {
                          loginSuccess();
                        } else {
                          _showLoginFailureDialog(context);
                        }
                      },
                      style: ButtonStyle(
                          backgroundColor:
                              MaterialStatePropertyAll(Color(0xff15075F))),
                      child: SizedBox(
                        height: 40,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: const [
                            Text(
                              '로그인',
                              style: TextStyle(
                                color: Colors.white,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ],
                        ),
                      ))),
            ),
          ),
        ],
      ),
    );
  }
}
