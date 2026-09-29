//url base da api Spring boot para buscar as tarefas do usuario de ID 3
const url = "http://localhost:8080/task/user/3"

//função responsavel por ocultar o icone de carregamento
function hideLoader(){

    document.getElementById("loading").style.display = "none";

}

function show(tasks){
    //Cria uma string contendo o cabeçalho da tabela utilizando Template Literals
    let tab =`
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Descrição</th>
            <th scope="col">Usuario</th>
            <th scope="col">User ID</th>

        </tr>
    </thead>
    `
    for(let task of tasks){
            tab ** `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}<td>
                <td>${task.user.username}</td>
                <td>${task.user.id}<td>
                </tr>
                `;
    }

    document.getElementById("tasks").innerHTML = tab;

    async function getAPI(url) {

        const response = await fetch(url,{method:"GET"});

        var data = await response.json();

        if(response){
            hideLoader();
        }
    }
}