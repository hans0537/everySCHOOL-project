import 'package:flutter/material.dart';

class CommunityBoard extends StatefulWidget {
  const CommunityBoard({super.key});

  @override
  State<CommunityBoard> createState() => _CommunityBoardState();
}

class _CommunityBoardState extends State<CommunityBoard> {
  var boardList = [
    {'title': '짧은공지제목', 'date': '2023.10.30.11:23'},
    {'title': '짧은공지제목짧은공지제목', 'date': '2023.10.30.10:43'},
    {'title': '긴공지제목긴공지제목긴공지제목', 'date': '2023.10.30.09:23'},
    {'title': '긴공지제목긴공지제목긴공지제목', 'date': '2023.10.29.23:01'},
    {
      'title': '아주긴공지제목아주긴공지제목아주긴공지제목아주긴공지제목아주긴공지제목',
      'date': '2023.10.28.16:33'
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20))),
      child: Container(
        // padding: EdgeInsets.all(0),
        margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(
            '학교 공지사항',
            style: TextStyle(fontSize: 22, fontWeight: FontWeight.w800),
          ),
          Container(
              height: 200,
              margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade400, // 테두리 색상
                  width: 1, // 테두리 두께
                ),
                borderRadius: BorderRadius.circular(8),
              ),
              child: ListView.builder(
                  physics: NeverScrollableScrollPhysics(),
                  itemCount: boardList.length,
                  itemBuilder: (context, index) {
                    return Container(
                      height: 38,
                      padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
                      margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            SizedBox(
                              width: MediaQuery.of(context).size.width * 0.6,
                              child: Text(
                                (boardList[index]['title'] as String).length >
                                        15
                                    ? '${(boardList[index]['title'] as String).substring(0, 15)}...'
                                    : boardList[index]['title'] as String,
                                style: TextStyle(fontSize: 17),
                              ),
                            ),
                            // Text(
                            //   boardList[index]['date'] as String,
                            //   style: TextStyle(color: Color(0xff999999)),
                            // ),
                            Image.asset('assets/images/community/new.png'),
                          ]),
                    );
                  })),
        ]),
      ),
    );
  }
}