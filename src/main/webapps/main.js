const tableBody = document.querySelector("tbody");
let xhr = new XMLHttpRequest();
xhr.open("GET", "http://localhost:8082/todo-demo/tasks");
xhr.onreadystatechange = function() {
    if(xhr.readyState === 4) {
        let taskJson = xhr.responseText;

        let tasks = JSON.parse(taskJson);

        tasks.forEach(task => {
            tableBody.insertAdjacentHTML("beforeend", `<td>${task.taskName}</td><td>${task.description}</td><td>${task.dueDate}</td><td>${task.completed ? "Complete" : "Incomplete"}</td>`);
        });
    }
}
xhr.send();

document.querySelector('#submit-btn').addEventListener("click", createTask);

function createTask(e) {
    e.preventDefault();

    const name = document.querySelector('#tname').value;
    const description = document.querySelector('#desc').value;

    let task = {taskName: name, description: description};

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8082/todo-demo/tasks");
    xhr.onreadystatechange = function() {
        if(xhr.readyState === 4) {
            //process response
            if(xhr.status === 201) {
                console.log("added in new task");

                let tableRow = document.createElement('tr');

                tableRow.innerHTML = `<td>${task.taskName}</td><td>${task.description}</td><td>${task.dueDate}</td><td>${task.completed ? "Complete" : "Incomplete"}</td></tr>`;
                tableBody.appendChild(tableRow);
            } else {
                console.log(`there was an issue creating the task --- status code: ${xhr.status} ${xhr.statusText}`);
            }
        }
    }

    let requestPayload = JSON.stringify(task);
    xhr.send(requestPayload);
}