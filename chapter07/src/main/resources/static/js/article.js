// 삭제기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click',event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`,{
            method: 'DELETE'
        })
        .then(() => {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles')
        });
    });
}
// 수정기능
// 1.id가 modify-btn 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn')
if (modifyButton) {
    modifyButton.addEventListener('click',event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`,{
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
            alert('수정이 완료되었습니다.');
            location.replace('/articles');
            //`/articles/${id}`
        });
    });
}
// 등록 기능
// id가 create-btn 인 엘리먼트
const createButton = document.getElementById("create-btn");

if (createButton){
    // 2. 클릭 이벤트가 감지되면 생성API 요청
    createButton.addEventListener("click", (event) =>{
        fetch("api/articles",{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
            title: document.getElementById("title").value,
            content: document.getElementById("content").value,
            }),
        }).then(() => {
            alert('등록이 완료되었습니다.');
            location.replace('/articles')
        });
    });
}