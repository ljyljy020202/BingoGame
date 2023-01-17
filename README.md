# BingoGame
Java Swing으로 구현한 영단어 빙고게임

컴퓨터와 사용자가 번갈아가며 플레이하는 영단어 빙고게임을 구현하고 최근까지 유지보수 하였습니다.
시작 화면에서 사용자가 빙고판의 크기를 선택하고 게임 시작을 누르면 N*N빙고판을 생성하여 띄워줍니다.
사용자와 컴퓨터가 한 번씩 단어를 선택한 뒤 빙고 여부를 체크합니다. 이 때, 컴퓨터가 랜덤 선택이 아닌 승리를 위한 선택을 하도록 알고리즘을 구현하였습니다.
영단어는 word.txt라는 파일로부터 읽어와 단어장을 생성하여 이용하고, 게임이 끝날 때마다 승,패,무 횟수를 record.txt라는 파일에 입력하여, 승률을 계속 누적하였습니다.
Swing으로 GUI를 구현하며 컴포넌트 배치와 이벤트 처리에 대해 학습하였습니다.

<주요 기능>

- 시작 화면

프로그램을 실행하면 시작 화면에 BINGO GAME이라는 타이틀과 게임 전적 및 승률, 그리고 빙고판의 크기를 선택하는 라디오버튼 게임 시작 버튼 등을 표시합니다.
( * 이미지 출처 : Cartoon Network )

![image](https://user-images.githubusercontent.com/105623744/212489895-e651ed31-3e90-47ec-ac1f-08e70dfa405e.png)

- 게임 시작

게임을 시작하면 선택한 N(빙고판의 크기)의 값에 따라 N*N의 빙고판을 생성하여 보여줍니다.

![image](https://user-images.githubusercontent.com/105623744/212490058-1c9a476e-a8db-41df-972f-692f79e54f61.png)

하단의 입력창에 사용자가 영단어를 입력하면 그 단어의 배경색을 칠해 선택되었음을 보여줍니다.
하단 좌측에는 사용자가 선택한 영단어와 뜻, 하단 우측에는 컴퓨터가 선택한 영단어와 뜻을 표시합니다.

![image](https://user-images.githubusercontent.com/105623744/212490091-6ee9389a-56ab-46bc-a12a-c15c371566b8.png)

게임을 계속 진행하다가 승부가 결정되면 팝업 다이얼로그로 결과와 승률을 띄워줍니다.

![image](https://user-images.githubusercontent.com/105623744/212490104-f037e907-d904-4b0b-9416-7af82a2d7b5b.png)


<주요 소스코드와 알고리즘>

- 파일로부터 영단어와 전적 기록 읽어오기

main에서 Frame 객체를 생성하면 Frame의 멤버로 있는 VocManager객체인 voc의 makeVoc()로 단어장을 생성합니다.
(영어 단어장 프로그램에서 이용했던 VocManager, Word 클래스를 변형하여 이용하였습니다.)

단어장을 생성한 뒤 전적 파일을 읽어 오는데, 전적 파일은 승리, 패배, 무승부 횟수가 기록되어 있습니다. 
전적 파일로부터 전적을 읽어와 승률을 계산하여 저장합니다. 기록이 없을 경우 모든 기록을 0으로 초기화합니다.

MyFrame 객체가 생성되면 다음과 같은 순서로 파읽을 읽어와 초기화한 뒤 프레임을 띄웁니다.

<img width="461" alt="image" src="https://user-images.githubusercontent.com/105623744/212968455-376c137f-de22-4036-a9a6-b78eb3f340a1.png">

- 빙고판 크기를 선택한 뒤 시작버튼 누르기

![image](https://user-images.githubusercontent.com/105623744/212489895-e651ed31-3e90-47ec-ac1f-08e70dfa405e.png)

시작 화면에는 빙고판 크기를 선택할 수 있는 라디오버튼과 그 옆에 시작 버튼이 있습니다. 이 부분은 라디오버튼, 시작버튼 각각의 이벤트 처리로 구현하였습니다.

라디오버튼의 선택을 변경할 때마다 num이라는 변수의 값이 변경됩니다.

시작 버튼 이벤트처리는 num이 0보다 큰 경우(라디오버튼에서 값을 선택했을 때)에만 실행되는데, VocManager에 있는 makeBingo(num)이라는 메소드로 컴퓨터와 사용자 각각의 빙고판을 생성한 뒤,
기존에 패널2에 붙어있던 컴포넌트를 모두 제거하고, 패널2의 레이아웃을 num*num의 GridLayout으로 변경하여 사용자 빙고판을 나타내 줍니다.
패널2 아래에는 영단어를 입력할 수 있는 창과 선택 메세지를 표시하는 패널3을 붙입니다.

시작버튼의 이벤트처리

<img width="433" alt="image" src="https://user-images.githubusercontent.com/105623744/212491906-9cae3784-4574-4fec-aa22-50b389683eb5.png">

![image](https://user-images.githubusercontent.com/105623744/212490058-1c9a476e-a8db-41df-972f-692f79e54f61.png)

- 게임 시작

게임의 한 턴은 사용자가 하단의 입력창에 영단어를 입력함으로써 이루어집니다. 
한 턴은 사용자가 단어를 선택-컴퓨터가 단어를 선택-승리/패배/무승부 여부 결정 순으로 이루어져 있습니다.

하단의 입력창은 JTextField 객체인 engWord인데, 단어를 입력하면 engWord의 이벤트 처리에서 startGame()메소드로 게임 진행을 시작합니다.

<img width="268" alt="image" src="https://user-images.githubusercontent.com/105623744/212492120-1d9878fa-2979-4314-85d4-beb29b3ec8cf.png">

startGame()은 다음의 순서로 진행됩니다.

userTurn() : 사용자로부터 입력받은 단어가 제대로 된 입력일 경우 빙고판에 체크하고 컴퓨터의 빙고판에도 그 단어가 존재하면 컴퓨터의 빙고판도 체크한 뒤, 영단어와 뜻을 출력해 준다.

comTurn() : 컴퓨터가 영단어를 선택하는 알고리즘으로 선택된 단어를 반환하고, 영단어와 뜻을 출력한다.

comTurnUser() : 컴퓨터가 선택한 영단어가 사용자의 빙고판에 존재하면 사용자의 빙고판을 체크한다.


사용자와 컴퓨터가 단어를 한 번씩 선택하면 빙고를 체크하는데, 이는 checkBingo라는 메소드로 이루어져 있습니다.
checkBingo는 사용자와 컴퓨터 각각의 빙고 개수를 반환합니다.
이후, 사용자와 컴퓨터의 빙고 개수를 비교하여 승패, 무승부 여부를 확인하여 종료조건을 만족하면 종료합니다. 
아닐 경우 다시 입력창에 영단어를 입력 받는 방식으로 게임을 진행한다.

게임 결과는 팝업 다이얼로그로 누적 승률과 함께 띄워줍니다.

<img width="921" alt="image" src="https://user-images.githubusercontent.com/105623744/212492257-17f93a9d-f934-4a7b-bf84-21af607e04c6.png">

- 컴퓨터의 승리를 위한 알고리즘

이 프로그램은 사용자와 컴퓨터 간의 빙고게임을 구현한 것입니다. 사용자가 승리를 위해 더 유리한 위치의 영단어를 선택하므로, 컴퓨터 역시 빙고판에서 랜덤으로 단어를 선택하는 것이 아닌, 
승리를 위한 알고리즘을 고안하여 선택하도록 하였습니다.

먼저, 컴퓨터 승리를 위한 알고리즘은 두 가지를 고려하였습니다.

1. 컴퓨터 빙고판의 가로, 세로, 대각선에서 하나의 단어만 추가로 선택하면 빙고가 되는 경우 

2. 선택된 단어들의 가로, 세로, 대각선에 가장 많이 해당되는 단어를 선택

1번을 체크하기 위해 각 행, 열, 대각선에 대해 몇 개의 단어들이 선택되어 있는지 체크하고 그 수가 num보다 1 작을 경우 그 줄에서 유일하게 선택되지 않은 단어를 선택 처리합니다.

<img width="669" alt="image" src="https://user-images.githubusercontent.com/105623744/212492620-958e2aae-8652-4005-b4bd-6be432f1c82a.png">

위와 같이 각 줄에서 단어들을 하나씩 확인하면서 선택된 경우 rowCount를 1만큼 증가시키고 선택되지 않은 경우 noneidx(선택되지 않은 단어의 인덱스)를 그 단어의 인덱스로 바꾸어 줍니다. 이것을 열, 양쪽 대각선에 대해서 다 수행합니다.
rowCount가 num보다 1만큼 작을 경우 그 줄에서 유일하게 선택되지 않은 단어를 선택 처리하고, 그렇지 않을 경우에 count가 0보다 크면 그 줄에서 선택된 단어가 있다는 뜻이므로 2번을 수행하기 위해 그 단어의 count(Word 클래스의 멤버로, 각 단어가 선택된 단어의 같은 줄에 몇 번이나 해당되는지 저장함)를 rowCount만큼 증가시킵니다.

2번을 체크하기 위해 1번 과정에서 단어를 선택하지 못한 경우 컴퓨터 빙고판의 모든 단어들에 대해 count가 가장 큰 단어(max)를 찾아 그 단어를 선택합니다.

<img width="341" alt="image" src="https://user-images.githubusercontent.com/105623744/212492712-909815b2-5673-4baa-880e-37b1d412d35a.png">

<수정 내용과 향후 개선 방향>

- 파일이 없는 경우 예외처리 : 단어 파일과 전적 파일이 없는 경우 각각에 대해 원래는 예외 메세지만 출력하고 프로그램이 제대로 종료되지 않았던 점을 정상적으로 종료되도록 수정하였다.
- 게임 시작 시 textfield에 focus주기 : 게임을 시작할 때 영단어 입력창이 선택되어있지 않아 입력하려면 마우스로 창을 한 번 클릭해야하는 번거로움이 있었는데, requestFocus()를 이용해 시작 시 바로 입력창에 단어를 입력할 수 있도록 하였다.
- 클래스 설계 : 기존에는 main에서 VocManager객체를 생성한 뒤 단어장을 생성하고, 프레임을 만든 뒤 프레임의 멤버로 또 VocManager객체를 생성하여 main에서 생성한 객체와 같은 객체를 가리키도록 하였는데
main에서 프레임만 생성하고 프레임 내에서 VocManager객체를 생성하여 단어장을 만드는 것으로 수정하였다.
또, 현재는 MyFrame클래스에 GUI관련 코드 뿐만 아니라 빙고 게임 처리 관련 코드들도 모두 포함이 되어 있는데, 향후에 MyFrame클래스에는 최대한 GUI관련 부분만 남기고, 게임 처리 관련된 부분은 별도의 클래스에서 처리하도록 수정할 계획이다.

