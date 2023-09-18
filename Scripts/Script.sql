# 첫번째(?) 최종 DB.
# https://www.erdcloud.com/d/bzE3DCYgXfSKnCw2y 또는
# erdcloud.com의 팀 ERD 항목의 '콘서트test1 -sb' 참고.

use concert;
# 만약 존재한다면 테이블 삭제해라.
# 삭제할 경우 외래키를 참조하고 있는 테이블을 먼저 삭제해주셔야 삭제가 가능합니다.
DROP TABLE IF EXISTS admin , user;

## 사용자 테이블 #############################################################
# 중복을 피하기 위하여 유저가 게시글에서 사용할 별명과
# 로그인시의 login id를 유니크 키로 설정 했습니다.
CREATE TABLE `user` (
    id          BIGINT         NOT NULL   AUTO_INCREMENT ,
    login_id    VARCHAR(50)    NOT NULL ,
    password    VARCHAR(20)    NOT NULL ,
    name        VARCHAR(50)    NOT NULL ,
    nickname    VARCHAR(20)    NOT NULL ,
    address     VARCHAR(300)   NOT NULL ,
    age         int            NOT NULL ,
    gender      tinyint        NOT NULL ,
    tel         VARCHAR(50)    NOT NULL ,
    PRIMARY KEY (id) ,
    UNIQUE KEY uk_login_id (login_id) ,
    UNIQUE KEY uk_nickname (nickname)
);


## 관리자 테이블 #############################################################
# 사용자가 인식할 관리자를 구분하기 위해 별명을 주었고 이또한 겹치지 않도록,
# 관리자 login_id도 겹치지 않도록 유니크를 설정하였습니다.
CREATE TABLE `admin` (
    id                  BIGINT      NOT NULL  AUTO_INCREMENT PRIMARY KEY,
    admin_login_id      VARCHAR(50) NOT NULL ,
    admin_password      VARCHAR(20) NOT NULL ,
    admin_nickname      VARCHAR(20) NOT NULL ,
    admin_name          VARCHAR(20) NOT NULL ,
    delete_yn           TINYINT     NOT NULL DEFAULT FALSE ,
    UNIQUE KEY uk_nickname (admin_nickname),
    UNIQUE KEY uk_admin_login_id (admin_login_id)
);


## 좌석 테이블 #############################################################
# 좌석은 하나의 고유 객체 입니다.
# 따라서 seat_num은 중복될 수 없습니다. 기본키 설정하였습니다.
CREATE TABLE `seat` (
    seat_num    VARCHAR(20)     NOT NULL    primary key,
    seat_type   VARCHAR(20)     NOT NULL,
    price       INT             NOT NULL
);


## 콘서트 테이블 #############################################################
# 콘서트는 3일차로 진행한다고 가정하였습니다.
# 따라서 id(기본키)는 3개만 존재 합니다.
CREATE TABLE `concert` (
    id              BIGINT           NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    concert_name    VARCHAR(200)     NOT NULL ,
    concert_place   VARCHAR(100)     NOT NULL ,
    concert_date    DATETIME         NOT NULL
);


## 티켓 테이블 #############################################################
# 티켓은 좌석 + 콘서트 정보의 조합, 하나의 가상 객체 입니다.
# 따라서 외장키를 여럿 참조하고 있으며,
# 좌석 고유번호 + 콘서트 기본키를 복합키로 기본키 설정하였습니다.
# 티켓 = 구매자 + 담당자 + 콘서트 정보 + 좌석 정보
# 비회원 티켓 조회시 uuid는 중복 되어서는 안되기 때문에 유니크 설정하였습니다.
# 티켓의 상태는 TRUE | FALSE 로 구분됩니다. (유효한 티켓은 TRUE , 무효/취소 등은 FALSE)
CREATE TABLE `ticket` (
    seat_num        VARCHAR(20)     NOT NULL ,
    concert_id      BIGINT          NOT NULL ,
    user_id         BIGINT          NOT NULL ,
    admin_id        BIGINT          NOT NULL ,
    uuid            VARCHAR(100)    NOT NULL ,
    ticket_name     VARCHAR(150)    NOT NULL ,
    created_at      DATETIME        NOT NULL  DEFAULT NOW() ,
    status          TINYINT         NOT NULL  DEFAULT TRUE ,
    PRIMARY KEY (seat_num, concert_id),
    CONSTRAINT `fk_user_id` FOREIGN KEY (user_id) REFERENCES `user`(id) ,
    CONSTRAINT `fk_admin_id` FOREIGN KEY (admin_id) REFERENCES `admin`(id) ,
    CONSTRAINT `fk_seat_num` FOREIGN KEY (seat_num) REFERENCES `seat`(seat_num) ,
    CONSTRAINT `fk_concert_id` FOREIGN KEY (concert_id) REFERENCES `concert`(id),
    UNIQUE KEY uk_uuid (uuid)
);


## event 게시판 #############################################################
# 카테고리는 '진짜진짜최종.drawio' 참고
# 이벤트 게시판을 말하자면 이벤트 , 당첨자발표 , 자유 , 후기/리뷰 가 카테고리에 해당.
create table event (
                id bigint not null primary key auto_increment,
                admin_id bigint not null,
                title varchar(200) not null,
                writer varchar(100) not null,
                content text null,
                hit int default 0,
                created_at datetime not null default now(),
                category varchar(50) not null,
                foreign key (admin_id) references admin(id)
);
select * from  event e ;
/*insert into event_board (writer_id ,admin_id ,title ,content ,view_cnt ,file_name ,file_path ,created_at ,updated_at ,delete_yn ,category)
values
  (1, 1, 'test1', '1 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (2, 1, 'test2', '2 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (3, 1, 'test3', '3 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (4, 1, 'test4', '4 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (5, 1, 'test5', '5 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (6, 1, 'test6', '6 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (7, 1, 'test7', '7 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (8, 1, 'test8', '8 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (9, 1, 'test9', '9 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트'),
  (10, 1, 'test10', '10 번째 이벤트 내용입니다.', 0, '', '', NOW(), NOW(), 0, '이벤트');*/
 
 
## event 게시판 댓글 #############################################################
CREATE TABLE `event_comment` (
    id                BIGINT       NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
    event_board_id    BIGINT       NOT NULL ,
    comment_writer    VARCHAR(20)  NOT NULL ,
    comment_content   VARCHAR(200) NOT NULL ,
    created_at         DATETIME     NOT NULL   DEFAULT NOW(),
    updated_at        DATETIME     NOT NULL   DEFAULT NOW(),
    delete_yn         TINYINT      NOT NULL   DEFAULT FALSE,
    CONSTRAINT `fk_event_board_id` FOREIGN KEY (event_board_id)  REFERENCES `event_board`(id)
);


## QNA 게시판 #############################################################
create table qna (
                id bigint not null primary key auto_increment,
                admin_id bigint not null,
                title varchar(200) not null,
                writer varchar(100) not null,
                content text null,
                hit int default 0,
                created_at datetime not null default now(),
                category varchar(50) not null,
                foreign key (admin_id) references admin(id)
);

select * from qna;
insert into qna_board(user_id,admin_id, title, content, created_at, updated_at,delete_yn,response_yn,file_name,file_path,category)
			values
			(01, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불'), 
			(02, 1,'결제관련 문의','결제관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','결제'), 
			(03, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불'),
			(04, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불'), 
			(05, 1,'취소관련 문의','취소관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','취소'), 
			(06, 1,'취소관련 문의','취소관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','취소'), 
			(07, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불'), 
			(08, 1,'구매관련 문의','구매관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','결제'), 
			(09, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불'), 
			(10, 1,'환불관련 문의','환불관련하여 문의남깁니다', NOW(), NOW(), 0, 0, '', '','환불');
		
select * from qna_board;
		


## QNA 게시판 댓글 #############################################################
CREATE TABLE `qna_comment` (
    id                BIGINT       NOT NULL   AUTO_INCREMENT  PRIMARY KEY ,
    qna_board_id      BIGINT       NOT NULL ,
    comment_writer    VARCHAR(20)  NOT NULL ,
    comment_content   VARCHAR(200) NOT NULL ,
    created_at         DATETIME     NOT NULL   DEFAULT NOW(),
    updated_at        DATETIME     NOT NULL   DEFAULT NOW(),
    delete_yn         TINYINT      NOT NULL   DEFAULT FALSE,
    CONSTRAINT `fk_qna_board_id` FOREIGN KEY (qna_board_id) REFERENCES `qna_board`(id)
);

## 공지사항 게시판 #############################################################
create table notice (
                id bigint not null primary key auto_increment,
                admin_id bigint not null,
                title varchar(200) not null,
                writer varchar(100) not null,
                content text null,
                hit int default 0,
                created_at datetime not null default now(),
                category varchar(50) not null,
                foreign key (admin_id) references admin(id)
);




################## 여기서부터 가짜 데이터 #########################################



## 콘서트 dummy data #############################################################
# 데이터의 예시로 실제로 사용된 콘서트명이면서 가장 긴 이름을 사용해봤습니다.
INSERT INTO concert
(concert_name, concert_place, concert_date)
    value
    ('2023 틴탑위고락잇드랍잇탑잇헤이돈스탑잇팟잇라이브 1일차(2023 TEEN TOP we gonna rock it drop it top it hey don''t stop it pop it LIVE Day 1)' ,
     'LG아트센터' , '2023-09-09 20:30:00');
INSERT INTO concert
(concert_name, concert_place, concert_date)
    value
    ('2023 틴탑위고락잇드랍잇탑잇헤이돈스탑잇팟잇라이브 2일차(2023 TEEN TOP we gonna rock it drop it top it hey don''t stop it pop it LIVE Day 2)' ,
     'LG아트센터' , '2023-09-10 18:30:00');
INSERT INTO concert
(concert_name, concert_place, concert_date)
    value
    ('2023 틴탑위고락잇드랍잇탑잇헤이돈스탑잇팟잇라이브 3일차(2023 TEEN TOP we gonna rock it drop it top it hey don''t stop it pop it LIVE Day 3)' ,
     'LG아트센터' , '2023-09-11 21:30:00');




## user dummy data #############################################################
# 사용자 로그인 아이디(email)는 중복될 수 없습니다.
# UNIQUE 설정 해 놓았기 때문에 중복된 데이터는 DB에서 걸러지게 됩니다..
##################################################################################
## 하지만 시도는 하지 마시길 바랍니다.
## 시도하셨다면 레코드를 삭제하시고 다시 데이터를 넣어주세요.
## user의 id는 AUTO_INCREMENT 가 설정 되어 있습니다.
## 해당 설정은 DB가 중복으로 데이터를 insert , update 시도시 실패로 추가하지는 않지만
## id는 증가하게 됩니다. 따라서 다음으로 올바른 데이터를 넣을시,
## 1>2>4 와 같이 id의 순차적인 나열이 깨집니다.
##################################################################################

INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('latrisha.homenick@yahoo.com', 'mypassword123', 'Derrick Mann DVM', 'anette.rutherford', '4493 Gerri Center, Sunniview, IL 80627', 49, 0, '1-726-543-4272 x20940');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('gertrud.schmitt@hotmail.com', 'mypassword123', 'Dana Bauch', 'grover.mitchell', 'Suite 735 152 Mitchell Overpass, North Ileana, NY 98583-9844', 30, 0, '(571) 082-6180 x94471');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('darrell.morissette@gmail.com', 'mypassword123', 'Shirley Rau DDS', 'rasheeda.bruen', '90539 Augustus Ramp, Marquardtport, WA 95429', 20, 0, '(458) 072-7169 x6117');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('marcelino.feest@hotmail.com', 'mypassword123', 'Juanita Hahn', 'eula.pfannerstill', 'Suite 831 37312 Titus Way, Lake Wen, MT 15422-4570', 36, 0, '(274) 195-1822 x521');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('autumn.feil@gmail.com', 'mypassword123', 'Connie McDermott Jr.', 'venice.cremin', 'Apt. 256 2108 Jacki Glens, Allafort, SC 28977', 19, 0, '915.637.7259 x09947');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('alan.yundt@hotmail.com', 'mypassword123', 'Amina Gerhold', 'ollie.hermiston', '48536 Bryce Port, Charlesettaburgh, WI 33827', 22, 0, '567-292-4112');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('theo.crona@gmail.com', 'mypassword123', 'Xavier Gibson I', 'luther.johns', 'Apt. 104 6821 Feil Manor, Lake Ceola, MT 96661-5345', 38, 0, '312.638.2255');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('manuel.gorczany@gmail.com', 'mypassword123', 'Tawanda MacGyver', 'rudy.upton', '81667 Dibbert Island, New Heathmouth, MA 14301', 30, 0, '089-247-4798 x95191');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kristi.mclaughlin@hotmail.com', 'mypassword123', 'Crissy Ondricka', 'lavina.keeling', '2829 Becker Manor, Haneland, MO 20724-2356', 57, 0, '426.663.6070 x6270');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kaitlin.ratke@hotmail.com', 'mypassword123', 'Wilson Doyle', 'leigh.hansen', 'Suite 121 2217 Rath Stravenue, Ardeliaborough, WV 73413-4914', 40, 0, '1-844-491-4121');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('laureen.towne@hotmail.com', 'mypassword123', 'Lakisha Funk Sr.', 'seth.ward', '371 Jaime Cove, North Noreen, CT 94701', 51, 0, '(366) 891-6026 x90135');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('alona.collier@hotmail.com', 'mypassword123', 'Dana Jast', 'alden.bergnaum', '20136 Corene Creek, Hermanside, LA 02472', 24, 0, '1-429-766-5031 x740');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('isabel.abernathy@hotmail.com', 'mypassword123', 'Orlando Bradtke', 'ciara.bosco', '37338 Omar Station, East Maris, GA 91732', 23, 0, '(718) 703-7373 x39685');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('rashad.hoeger@yahoo.com', 'mypassword123', 'Jocelyn Jenkins', 'shelby.franecki', '8538 Mann Circles, Lake Augustuston, NH 55269-4195', 33, 0, '(674) 160-2758 x6923');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kerstin.langosh@hotmail.com', 'mypassword123', 'Joi Stracke', 'antone.balistreri', 'Suite 547 6129 Kiesha Isle, Kuhichaven, ID 57286', 34, 0, '416.688.8612 x3013');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('dawne.jakubowski@hotmail.com', 'mypassword123', 'Dr. Collen Hayes', 'malcolm.kemmer', 'Apt. 580 288 Steven Forest, Conniehaven, NY 09208', 40, 0, '672.332.1580');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('annice.boehm@hotmail.com', 'mypassword123', 'Lynn Murphy', 'loren.gusikowski', '99892 Roger Bypass, Boyleburgh, TX 53780', 58, 0, '723-834-4858 x5646');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lan.keebler@hotmail.com', 'mypassword123', 'Hassan Rempel', 'lynwood.rempel', '24686 Bradley Springs, West Hectormouth, WI 62759', 34, 0, '253-775-4948 x6601');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('flo.medhurst@gmail.com', 'mypassword123', 'Alec Fahey', 'thanh.braun', '3168 Lilliana Circle, West Morganberg, VA 99277-3833', 29, 0, '067-025-2476');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lindsay.haley@yahoo.com', 'mypassword123', 'Janiece Carroll', 'gilberto.mosciski', 'Apt. 032 6692 Leopoldo Shores, New Harrietteshire, MN 82597-5523', 26, 0, '898.505.8053 x8808');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('man.jenkins@yahoo.com', 'mypassword123', 'Dong Rowe', 'mittie.hahn', '13824 Angelina Ports, South Young, IL 59723', 54, 0, '258-919-3892 x1747');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('vernita.marks@gmail.com', 'mypassword123', 'Alberto Vandervort', 'genevie.leannon', 'Suite 868 05586 Catharine Hollow, West Nikki, NM 44267-1702', 42, 0, '205.925.1641 x50686');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('marquis.robel@hotmail.com', 'mypassword123', 'Eleanore Littel', 'jules.simonis', '7295 Alleen Forge, New Erinnborough, FL 17239-6832', 46, 0, '427-346-7379');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('dierdre.fritsch@hotmail.com', 'mypassword123', 'Roosevelt Kilback', 'trenton.lemke', 'Apt. 989 9797 Bosco Shoal, South Angle, LA 55852', 53, 0, '1-287-567-7002 x7185');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('danial.wyman@gmail.com', 'mypassword123', 'Miss Coralie Rau', 'jasper.bergstrom', 'Suite 463 2794 Armanda Spurs, Jastland, AL 13044-5258', 26, 0, '1-807-340-0394 x3997');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('rey.maggio@hotmail.com', 'mypassword123', 'Maxwell Reynolds', 'maud.murazik', 'Apt. 377 575 Rolfson Gateway, East Aleenstad, WY 56619', 24, 0, '(389) 369-3030 x791');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('ivory.zboncak@hotmail.com', 'mypassword123', 'Rosy Corkery', 'reuben.gibson', '296 Cythia Underpass, North Vita, OK 54524-8423', 42, 0, '518-227-5729 x14764');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jesus.lind@hotmail.com', 'mypassword123', 'Buffy Swift', 'shanice.wiza', '541 Stuart Overpass, Lake Charmainefurt, ND 03455-8889', 38, 0, '(295) 930-0006 x264');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('farrah.muller@gmail.com', 'mypassword123', 'Shelby Flatley', 'roselle.lindgren', '4155 Rodney Plain, Lake Reiko, SC 95166', 36, 0, '985.920.9188 x5865');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('colby.oberbrunner@hotmail.com', 'mypassword123', 'Jewel Waters', 'wendell.haag', '9060 Greenfelder Centers, Lizziefort, IL 43891', 43, 0, '019-472-5940 x9399');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('rosann.rogahn@yahoo.com', 'mypassword123', 'Kendrick Gutmann II', 'melvin.hudson', '655 Lon Underpass, Uptonside, AK 67819', 54, 0, '1-852-281-2707 x7214');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('hassan.franecki@hotmail.com', 'mypassword123', 'Shonta Mayer', 'carey.vonrueden', '57520 Gordon Villages, Medhurstmouth, RI 72150-3520', 39, 0, '868-061-0132 x556');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('carolyne.kassulke@hotmail.com', 'mypassword123', 'Sheri Rutherford', 'merilyn.bins', 'Suite 211 70718 Cheryl Parks, West Betsey, IL 67399-7473', 40, 0, '1-376-579-3703');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('georgianne.walker@gmail.com', 'mypassword123', 'Jay Gislason', 'wilmer.stiedemann', 'Apt. 806 866 Veum Prairie, South Luann, OK 65527-2880', 54, 0, '805.500.1903 x39817');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('ruben.lowe@hotmail.com', 'mypassword123', 'Elna Konopelski', 'mechelle.mckenzie', 'Apt. 128 2510 Steuber Camp, Johnsonmouth, WI 19377', 19, 0, '(548) 585-0015 x406');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lila.spencer@gmail.com', 'mypassword123', 'Karen Dickinson', 'jesusita.breitenberg', 'Suite 030 023 Rosalina Summit, Fadelhaven, PA 52489-4314', 34, 0, '(868) 256-5330 x85481');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('annice.watsica@hotmail.com', 'mypassword123', 'Claude Walter', 'kai.wisoky', '36618 Torp Cape, New Tonisha, OH 94981', 32, 0, '(449) 865-2102 x36195');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jess.hills@yahoo.com', 'mypassword123', 'Adrian Dach', 'blondell.brown', '2721 Bradtke Stravenue, North Gayle, IN 06442', 23, 0, '022.493.5839 x9891');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('elias.kozey@yahoo.com', 'mypassword123', 'Jimmy Feil Sr.', 'armand.hudson', 'Suite 111 85791 Marhta Knolls, South Shirley, NM 34027-0836', 54, 0, '593.511.9823 x349');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('ward.schaefer@gmail.com', 'mypassword123', 'Elwood Lynch', 'celena.kuhic', 'Suite 459 566 Thiel Run, North Jestine, NM 10951', 27, 0, '(819) 742-5044');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('abdul.zemlak@hotmail.com', 'mypassword123', 'Aldo Pfeffer Jr.', 'tiffaney.larson', '4670 Kaitlyn Causeway, New Robview, MN 55733', 19, 0, '925.330.8766 x6231');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('donetta.murphy@yahoo.com', 'mypassword123', 'Mr. William Jenkins', 'daren.moore', 'Apt. 916 523 Gulgowski Falls, Dawnshire, RI 16760', 35, 0, '1-623-595-7929 x704');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('divina.shanahan@gmail.com', 'mypassword123', 'Wade Brekke', 'daryl.crooks', 'Apt. 651 793 Marni Dale, Port Antony, PA 37873', 32, 0, '(933) 133-8969');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('bertie.hammes@hotmail.com', 'mypassword123', 'Vernon Lynch', 'mallory.bode', 'Apt. 688 33600 Freddy Pike, Josetteborough, CA 48551', 33, 0, '838-663-8772');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('travis.paucek@hotmail.com', 'mypassword123', 'Mr. Johana Tillman', 'loyd.jenkins', 'Apt. 745 93379 Runolfsson Port, Lebsackbury, SC 51016-6898', 37, 0, '(057) 764-5429');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('melodie.parisian@hotmail.com', 'mypassword123', 'Dr. Eugene Altenwerth', 'elisabeth.simonis', 'Apt. 380 843 Minh Island, New Sindy, ND 79586-9437', 45, 0, '1-641-390-8987 x2363');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('cleveland.romaguera@gmail.com', 'mypassword123', 'Clarine Funk', 'keira.herzog', 'Suite 826 64582 Sandee Brook, Morissettechester, MI 04644', 36, 0, '(023) 425-7386');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('loma.schoen@gmail.com', 'mypassword123', 'Arcelia Reichert', 'eula.schumm', 'Apt. 037 29575 Haag Ways, West Teodoratown, SD 93155', 23, 0, '(729) 126-8687');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jose.mcdermott@yahoo.com', 'mypassword123', 'Clifford Thompson', 'arlen.waters', '6698 Batz Street, West Jermaineview, NY 33660-0411', 37, 0, '1-969-568-0892');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('theron.jacobs@hotmail.com', 'mypassword123', 'Gita Feest', 'sheron.gleason', 'Apt. 920 67906 Joey Falls, Lake Carmonhaven, IL 46612-9851', 38, 0, '609-053-3191 x67130');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('mary.streich@yahoo.com', 'mypassword123', 'Ron Jast PhD', 'delta.yost', 'Suite 748 56130 Sanford Ridges, Port Loraine, PA 20410-7731', 56, 0, '1-330-722-4092');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('tawana.okon@hotmail.com', 'mypassword123', 'Selene Stehr', 'paul.hane', 'Suite 636 7195 Collins Summit, Feilstad, KS 79180-3642', 54, 0, '(005) 975-0420');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('porsche.wuckert@hotmail.com', 'mypassword123', 'Lena Carroll', 'elisha.doyle', 'Apt. 641 920 Wehner Cliff, Lake Heidestad, MS 45753-4066', 43, 0, '(131) 845-8491 x3818');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('garfield.erdman@gmail.com', 'mypassword123', 'Cicely Schmidt V', 'keneth.blick', 'Apt. 135 636 Bayer Loop, Port Emeldaport, AL 07186', 38, 0, '(715) 776-6466');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('raymundo.metz@yahoo.com', 'mypassword123', 'Stefani Lowe', 'rosario.bednar', 'Suite 330 7988 Von Road, Kutchport, CO 65637', 28, 0, '236-851-7596 x8398');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('timmy.strosin@hotmail.com', 'mypassword123', 'Tanner Goldner Jr.', 'marietta.heller', '17072 White Key, Silviabury, DE 05430-7723', 58, 0, '243.103.1807 x391');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('greg.becker@hotmail.com', 'mypassword123', 'Evelyne Witting', 'evan.bahringer', 'Suite 872 0303 Erick Key, Jacobston, IN 07073-2166', 35, 0, '417.164.6707 x4144');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jackeline.pollich@gmail.com', 'mypassword123', 'Johnnie Raynor PhD', 'milton.cartwright', '97164 Moore Valley, Bradfordbury, NH 97876-3737', 54, 0, '(737) 334-2154 x2116');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('enrique.kautzer@yahoo.com', 'mypassword123', 'Emil Collier', 'kirsten.russel', '18036 Sammy Fork, Lake Trangview, SC 20078', 28, 0, '(035) 256-4190 x07464');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('norris.brakus@yahoo.com', 'mypassword123', 'Miss Johnnie Corkery', 'daren.flatley', 'Apt. 306 570 MacGyver Drives, Lake Marielleport, ND 81395', 38, 0, '468.845.9178 x091');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kati.upton@yahoo.com', 'mypassword123', 'Zana Barrows II', 'vivienne.hand', '64051 Danica Brook, West Elwoodland, LA 99590-9637', 20, 0, '173-052-2102');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('reginia.stamm@hotmail.com', 'mypassword123', 'Victor Fadel I', 'dudley.jacobi', 'Apt. 264 330 Keeling Way, East Elmo, NV 20339-3632', 23, 0, '(765) 966-0543 x235');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('elvie.hamill@hotmail.com', 'mypassword123', 'Brendon Reynolds', 'barbie.ortiz', '03186 Kreiger Ports, Leschmouth, AK 53575', 44, 0, '549-193-5757 x704');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('stella.douglas@gmail.com', 'mypassword123', 'Stacie Koelpin', 'earnest.kovacek', 'Apt. 655 41015 Charlotte Unions, Jeremyhaven, OR 15050-3964', 53, 0, '1-852-521-0885');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('zachariah.luettgen@hotmail.com', 'mypassword123', 'Armandina Lesch', 'lenard.zieme', '3484 Elton Spring, Markshire, IL 32232-2677', 25, 0, '(949) 481-6747 x406');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('mary.fay@hotmail.com', 'mypassword123', 'Albert Schulist', 'joye.gottlieb', '9753 Lyman Islands, Hellerbury, WA 38729', 42, 0, '1-900-606-8539 x158');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('archie.mann@yahoo.com', 'mypassword123', 'Miss Kimber Bahringer', 'jefferey.ruecker', '6253 Winston Fork, Hahnburgh, TX 51166-9531', 50, 0, '944.132.6901 x211');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('margrett.buckridge@gmail.com', 'mypassword123', 'Cliff Abbott', 'quinn.zemlak', 'Suite 790 12168 Miquel Knolls, East Shaylaside, MN 67894-9138', 37, 0, '979.195.1428 x80043');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('claude.will@gmail.com', 'mypassword123', 'Salvatore Veum III', 'adena.schuster', 'Apt. 030 127 Annamae Well, Parismouth, MA 23113-9871', 46, 0, '(996) 373-5284');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('adaline.braun@yahoo.com', 'mypassword123', 'Hettie Beer III', 'kerry.hansen', '531 Grazyna Island, Christiansenfurt, TX 25150', 36, 0, '1-445-912-6557 x615');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('julio.hegmann@gmail.com', 'mypassword123', 'Leonor Dickens', 'kirk.rowe', 'Apt. 848 9887 Huey Isle, New Nathanview, IN 87876-6486', 38, 0, '604-085-1774');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('carolyn.douglas@yahoo.com', 'mypassword123', 'Wade Hills', 'dong.kautzer', '5136 Cormier Locks, East Roniland, MO 01070', 27, 0, '287.356.4697 x89695');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('yadira.white@gmail.com', 'mypassword123', 'Emely Dooley I', 'wade.steuber', 'Suite 871 170 Elisa Isle, Rosaurastad, MD 20275', 57, 0, '1-500-873-3481 x649');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('dwayne.hickle@gmail.com', 'mypassword123', 'Elwood Gleason', 'arthur.renner', 'Apt. 265 639 Wehner Stravenue, Morissetteberg, ND 52110-3948', 28, 0, '315.677.7190');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('mellissa.haag@gmail.com', 'mypassword123', 'Suk Nader', 'idalia.tremblay', '24567 Von Roads, Torpbury, DE 38672-3168', 28, 0, '093.941.0720 x673');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('leon.kerluke@gmail.com', 'mypassword123', 'Ms. Harland Trantow', 'joey.tillman', 'Suite 951 9888 Sheilah Plaza, Fadelshire, OR 62027-6318', 49, 0, '(407) 063-2576 x64588');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lowell.bauch@yahoo.com', 'mypassword123', 'Miss Alton Bode', 'rufus.reichert', '8661 Margrett Shore, New Obdulia, NY 89944-2225', 45, 0, '(578) 274-5534 x02304');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('belkis.borer@yahoo.com', 'mypassword123', 'Else Mayert PhD', 'stan.kuhic', '5918 Zieme Circle, Raynorland, MT 34052-1922', 24, 0, '404-497-7433');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('brunilda.stokes@yahoo.com', 'mypassword123', 'Miss Sandy Connelly', 'deetta.hickle', '1230 Crona Flats, Lake Ehtelburgh, FL 08619-0803', 40, 0, '(271) 930-1824 x75205');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('benjamin.dietrich@yahoo.com', 'mypassword123', 'Mrs. Ulysses Botsford', 'riley.hoeger', 'Suite 312 65683 Hyatt Mission, Kristianmouth, NH 32646-5739', 32, 0, '1-743-309-5496');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('carina.sanford@gmail.com', 'mypassword123', 'Mrs. Suzy Simonis', 'casey.macejkovic', 'Suite 519 027 Weimann Vista, Stantonfort, TN 51035-9050', 25, 0, '589.613.1942');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('willetta.hodkiewicz@yahoo.com', 'mypassword123', 'Shirly Lakin', 'rubin.oreilly', 'Suite 910 43720 Ruecker Underpass, Randyshire, WV 78713-5694', 35, 0, '(469) 972-6937 x37890');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('sixta.volkman@gmail.com', 'mypassword123', 'Arlena Heaney', 'lorenzo.wunsch', '929 Beier Brooks, Keeblerhaven, SC 89097', 36, 0, '1-065-344-7619 x31359');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jordan.wintheiser@hotmail.com', 'mypassword123', 'Mr. Alexis Halvorson', 'leeann.orn', 'Suite 093 6718 Block Shoal, Lake Bridgette, MT 29106-1641', 28, 0, '753.775.5954 x4063');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('ira.kirlin@yahoo.com', 'mypassword123', 'Dennis Shields', 'larry.witting', '351 Fausto Crescent, New Loura, NV 08483', 59, 0, '950.439.1784');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('pierre.glover@hotmail.com', 'mypassword123', 'Shea Zboncak', 'marilyn.medhurst', 'Suite 513 42577 Lauri Shoals, Hermanmouth, OH 53562-1061', 55, 0, '150.281.7739 x67418');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('corrinne.gerlach@yahoo.com', 'mypassword123', 'Porfirio Hills V', 'frank.watsica', 'Apt. 502 84027 Cornelius Ports, East Ileenchester, NH 36009-4206', 43, 0, '513-643-6994');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('cherise.tromp@gmail.com', 'mypassword123', 'Linn Bartell', 'fred.oconnell', 'Suite 979 2941 Tremblay Light, Port Allanmouth, MT 26532', 48, 0, '111-613-8892 x848');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('noel.feest@yahoo.com', 'mypassword123', 'Michael Roob', 'isaac.lindgren', 'Suite 086 81200 Reichert Ramp, Jameefort, WY 07741-2727', 21, 0, '956-972-5993');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('darcy.grimes@gmail.com', 'mypassword123', 'Alton Bergstrom', 'calvin.johnston', '83733 Adan Stravenue, Lake Shannon, FL 71336', 18, 0, '(841) 646-5547');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lissette.greenfelder@gmail.com', 'mypassword123', 'Darius Kuhic', 'ambrose.grady', 'Apt. 056 091 Abbott Trail, North Francisca, VA 77871', 32, 0, '476-556-3772 x13729');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('porsha.homenick@gmail.com', 'mypassword123', 'Mrs. Tamar Hartmann', 'tracy.stokes', 'Apt. 804 3169 Metz Mountain, East Warnerview, ME 39645', 42, 0, '297-991-6840 x92191');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jermaine.bernhard@gmail.com', 'mypassword123', 'Mitchel Brown', 'arlean.hansen', '38369 Adams Manors, Port Preston, OR 48727-8654', 56, 0, '357.920.2071 x90220');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('rudolph.schamberger@hotmail.com', 'mypassword123', 'Leonida Feil', 'tyra.considine', 'Apt. 359 64199 Derrick Stravenue, North Dudleyberg, NV 99250', 45, 0, '173-900-7481 x7174');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('marshall.jerde@hotmail.com', 'mypassword123', 'Deandrea White', 'charlott.farrell', 'Suite 232 8210 Wolf Prairie, Lake Cecilyville, HI 35596', 43, 0, '(805) 775-4689');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kirstin.torphy@gmail.com', 'mypassword123', 'Dante Wolff', 'christoper.boyle', 'Apt. 350 047 Hoyt Mission, Randellfort, GA 74137-0860', 23, 0, '1-276-683-2689');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('shayne.kunde@hotmail.com', 'mypassword123', 'Will Hane', 'evia.gerhold', '132 Serina Brook, Pasqualemouth, CT 86935', 34, 0, '206.907.3186');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('kourtney.bernhard@hotmail.com', 'mypassword123', 'Elke Morar MD', 'marianela.larkin', '498 Mraz Bypass, Lake Arielton, CT 37135', 39, 0, '1-370-900-9618 x78451');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lucinda.torphy@yahoo.com', 'mypassword123', 'Manuel Fay', 'hal.cormier', 'Apt. 007 27283 Florentino Station, North Coryburgh, IN 83968-8143', 47, 0, '1-080-187-2945 x700');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('nidia.klein@hotmail.com', 'mypassword123', 'Nellie Raynor', 'tami.osinski', '462 Gerard Villages, Lake Nicky, MO 75218', 49, 0, '1-424-898-7658 x7070');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('rodger.hyatt@yahoo.com', 'mypassword123', 'Chara Parker', 'laura.blick', '3737 Rodolfo Vista, Ezrabury, TX 55749', 36, 0, '372.148.8349');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('randal.schmidt@gmail.com', 'mypassword123', 'Danial Purdy', 'rogelio.murray', 'Apt. 429 2127 Wiegand Inlet, Port Grayce, WY 51153-8365', 31, 0, '811.226.7851');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jayson.quitzon@gmail.com', 'mypassword123', 'Veda Kunze', 'jana.ohara', '5173 Bednar Square, Lonfort, SD 25873-5253', 34, 0, '1-524-312-6906');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('tai.conroy@hotmail.com', 'mypassword123', 'Everette Krajcik', 'lan.gutmann', '4691 Lita Station, West Tamerafort, KS 08498', 31, 0, '1-718-344-6640 x954');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('thersa.fahey@yahoo.com', 'mypassword123', 'Chauncey Kutch', 'vanetta.kemmer', 'Suite 402 6255 Denis Pine, Lannymouth, ID 50160', 26, 0, '1-220-392-3855 x3359');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lucas.hand@yahoo.com', 'mypassword123', 'Dusty Littel V', 'caren.hettinger', '0032 Jamaal Ways, O''Keefechester, WI 85905', 49, 0, '(828) 314-5273 x3115');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jannie.gislason@yahoo.com', 'mypassword123', 'Stacey Vandervort', 'gregg.waelchi', 'Apt. 743 7933 Jaskolski Corner, Fayberg, FL 53858', 25, 0, '065-673-6535');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('lizzette.bailey@yahoo.com', 'mypassword123', 'Cliff McDermott V', 'audrea.murphy', '10536 Basil Station, West Melberg, TN 22296', 40, 0, '1-551-128-8321');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('dung.torphy@yahoo.com', 'mypassword123', 'Antoine Flatley', 'gail.schultz', 'Apt. 402 504 Price Flats, East Katharineshire, NV 72978', 49, 0, '(267) 359-9927');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('devorah.langworth@yahoo.com', 'mypassword123', 'Cecile West', 'enola.reilly', '12397 Virgil Trail, West Latina, NJ 36059-0115', 37, 0, '(911) 503-9420');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('tyrell.mckenzie@hotmail.com', 'mypassword123', 'Nakisha Hills DVM', 'louis.rippin', 'Suite 904 03165 Prohaska Union, Schummberg, VA 53023-4701', 56, 0, '432-202-1858 x49220');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('enrique.romaguera@gmail.com', 'mypassword123', 'Mrs. Kasha Hamill', 'mirian.cronin', 'Suite 494 8787 Haley Parkways, Tatumside, SC 96729', 32, 0, '055.707.6707 x51199');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('tobie.ullrich@yahoo.com', 'mypassword123', 'Johnny Boyer', 'sheri.kuvalis', '3322 Adams Ford, Cassondraport, PA 96281', 20, 0, '534.325.4373 x7297');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('nam.ernser@gmail.com', 'mypassword123', 'Miss Micah Schmeler', 'jeramy.ledner', '569 Shirl Well, Glovermouth, AK 18597', 27, 0, '(120) 733-2774 x2233');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('olive.cole@hotmail.com', 'mypassword123', 'Faustino Torp II', 'kurt.yundt', 'Suite 582 36712 Babara Mall, Earnestfurt, IA 94799', 39, 0, '754-256-9107 x7011');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('leslie.rowe@yahoo.com', 'mypassword123', 'Norris Turner', 'mike.orn', '73371 Murray Lane, Catherinaland, ND 71547-3920', 53, 0, '789-845-6948 x4780');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('jeanice.jacobs@hotmail.com', 'mypassword123', 'Logan Nolan', 'wilton.boyer', 'Apt. 393 67590 Kemmer Well, South Marchelle, WY 99426-6195', 41, 0, '(917) 629-7841 x457');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('digna.goodwin@gmail.com', 'mypassword123', 'Chase Parisian', 'jazmin.oconnell', 'Apt. 267 06543 Gene Walks, Fritzshire, NM 25505-0661', 46, 0, '346.217.3724 x62825');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('damian.fay@hotmail.com', 'mypassword123', 'Shayne Schulist', 'vito.pagac', '9610 Haley Harbor, Annitaside, CA 79897-3411', 48, 0, '1-448-073-4724 x2653');
INSERT INTO user (login_id, password, name, nickname, address, age, gender, tel) VALUES ('melvin.wiegand@gmail.com', 'mypassword123', 'Rodolfo Gottlieb', 'mark.kuhn', 'Suite 796 78890 Francisco Views, Daughertyfort, OK 77565', 32, 0, '595.515.2896 x37203');




## admin dummy data #############################################################
# admin의 데이터도 위와 마찬가지입니다.
# 중복된 login_id , nickname을 넣을 경우 DB에서 걸러주기는 하지만 id는 증가함으로 순차성이 깨집니다.
# 우리는 id의 나열을 유지하기 위해 delete_yn을 사용하는 것이기도 합니다.

INSERT INTO admin
(admin_login_id, admin_password, admin_nickname, admin_name)
VALUES
    ('admin1@test.com' , 'adminadmin' , 'AdminMaster' , '김만식'),
    ('admin2@test.com' , '1q2w3e4!1' , 'RuleRuler' , '이해인'),
    ('admin3@test.com' , '1q2w3e4!2' , 'MonitorMagician' , '유재석'),
    ('admin4@test.com' , '1q2w3e4!3' , 'Jake' , '박거성'),
    ('admin5@test.com' , '1q2w3e4!4' , 'David' , '이현승'),
    ('admin6@test.com' , '1q2w3e4!5' , 'Todd' , '최승환'),
    ('admin7@test.com' , '1q2w3e4!6' , 'Tom' , '윤재진'),
    ('admin8@test.com' , '1q2w3e4!7' , '콘서트 요정' , '이지수'),
    ('admin9@test.com' , '1q2w3e4!8' , 'MoMo' , '송윤정'),
    ('admin10@test.com' , '1q2w3e4!9' , '김춘식' , '김춘식'),
    ('admin11@test.com' , '1q2w3e4!10' , 'adminBox' , '박혜선'),
    ('admin12@test.com' , '1q2w3e4!11' , 'mkmk' , '정이수'),
    ('admin13@test.com' , '1q2w3e4!12' , 'Zak' , '박창길'),
    ('admin14@test.com' , '1q2w3e4!13' , 'Denma' , '정민철');



## seat dummy data #############################################################
# 임의로 만든 데이터라서 VIP , R , S 로 분류하였습니다.
# A ~ F 구역으로 나뉘어 있고,
# 1 ~ 2번째 줄 까지는 6자리씩
# 3 ~ 5번째 줄 까지는 7자리씩 구성되어 있습니다.
# seat_num의 입력 값은 예시로 D-A1은 D구역 A열 1번째 자리입니다.

# D 구역(VIP)
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-A6' , 99000);

INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-B6' , 99000);

INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-C7' , 99000);

INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-D7' , 99000);

INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'D-E7' , 99000);

# C구역(VIP)
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-A6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-B6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-C7' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-D7' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E1' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E2' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E3' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E4' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E5' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E6' , 99000);
INSERT INTO seat (seat_type, seat_num, price) value ('VIP' , 'C-E7' , 99000);

# E 구역(R)
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-A6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-B6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-C7' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-D7' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'E-E7' , 88000);

# B구역 (R)
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-A6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-B6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-C7' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-D7' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E1' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E2' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E3' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E4' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E5' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E6' , 88000);
INSERT INTO seat (seat_type, seat_num, price) value ('R' , 'B-E7' , 88000);

# A구역 (S)
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-A6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-B6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-C7' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-D7' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'A-E7' , 77000);

# F구역 (S)
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-A6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-B6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-C7' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-D7' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E1' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E2' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E3' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E4' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E5' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E6' , 77000);
INSERT INTO seat (seat_type, seat_num, price) value ('S' , 'F-E7' , 77000);

## ticket dummy data #############################################################

INSERT INTO ticket(seat_num, concert_id, user_id, admin_id, uuid, ticket_name)
VALUES (
        'A-A1' , '1' , '1' , '1' , 'Aznq!12dwq@d1182' ,
        '2023 틴탑위고락잇드랍잇탑잇헤이돈스탑잇팟잇라이브 1일차(2023 TEEN TOP we gonna rock it drop it top it hey don''t stop it pop it LIVE Day 1)'
        );
# A-A1 좌석을 골랐고(seat_num)
# 콘서트는 1일차 콘서트(concert_id)
# 사용자는 1번 친구이고(user_id)
# 티켓의 담당자는 1번(admin_id)
# uuid는 본래라면 서비스로직에서 생성된 '임의의 값'
# 콘서트명은 어쩌구저쩌구 (ticket_name)

       
       select * from user;
      
      select * from event;
     select * from admin a ;
    
     insert into qna  (admin_id, title, writer, content, category)
     values(1, '제목', '나', '내용', 'test_1');
       