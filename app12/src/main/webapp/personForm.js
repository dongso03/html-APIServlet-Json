const personForm = document.getElementById("personForm");
personForm.addEventListener("submit", process);

const messageP = document.getElementById("message");

const btnCheck = document.getElementById("btnCheck");
btnCheck.addEventListener("click", checkName);

const inputName = document.getElementById("inputName");

const btnSubmit = document.querySelector("input[type=submit]");

inputName.addEventListener("input", resetSubmitBtn);

function resetSubmitBtn(e) {
    btnSubmit.disabled = true;
    messageP.innerHTML = "";
    messageP.innerText = "이름 중복을 확인해주세요"
}

function checkName(e) {
    let userName = inputName.value;
    let queryParam = new URLSearchParams();
    queryParam.set("name", userName);

    fetch("http://localhost:8080/api/person?" + queryParam.toString())
        .then(resp => {
            if (resp.status == 404) {
                messageP.innerHTML = "";
                messageP.innerText = "사용가능한 이름입니다."
                btnSubmit.disabled = false;
            } else {
                messageP.innerHTML = "";
                messageP.innerText = "중복으로 사용 불가능한 이름입니다."
                btnSubmit.disabled = true;
            }
        }).then(data => {
            
        })
}

function process(e) {
    e.preventDefault();

    // TODO: 비동기 요청 전에, 입력값 유효를 확인해야 한다.

    let formData = new FormData(personForm);
    let obj = Object.fromEntries(formData);
    let json = JSON.stringify(obj);

    fetch("http://localhost:8080/api/person", {
        method: "POST"
        , body: json
    }).then(resp => {
        let code = resp.status;

        if (code == 400 || code == 409) {
            return resp.text().then(t => { // Promise 구성
                throw new Error(t); // Error 클래스를 생성하고 throw하면 예외를 발생시킬 수 있음.
            });
        }

        if (code == 201) {
            messageP.innerText = "성공";
            personForm.reset();
        }
    }).catch(e => { // promise 처리 중 예외 처리
        let parsed = JSON.parse(e.message);
        messageP.innerHTML = ""; // 요소의 내부 HTML 표현 작성 가능
        for (let prop in parsed) { // for .. of(반복가능한 배열 등) 와 달리 객체의 property를 반복한다

            let p = document.createElement("p");
            p.innerText = parsed[prop];

            messageP.appendChild(p);
        }
    });
}