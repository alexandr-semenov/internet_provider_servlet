
function clearTbody(tbody) {
    tbody.empty();
}

function addTbody(tbody, response) {
    let edit = $("#editLabel").text();
    $.each(response, function (index, value) {
        tbody.append(
            '<tr>' +
            '<td valign="middle">' + value.name + '</td>' +
            '<td><a href="/admin/tariff/edit?id='+ value.id +'" class="btn btn-info text-white" type="button">' + edit + '</a></td>' +
            '</tr>'
        );
    });
}

function showSuccessAlert(message) {
    let alert = $("#success-alert");
    let body = alert.find(".alert-body");

    body.empty();
    body.append("<p>" + message + "</p>");

    alert.fadeIn();

    setTimeout(function(){
        alert.fadeOut();
    }, 4000);
}

function showErrorAlert(messages) {
    let alert = $("#error-alert");
    let body = alert.find(".alert-body");

    body.empty();
    for (let message of messages) {
        body.append("<p>" + message + "</p>");
    }

    alert.fadeIn();

    setTimeout(function(){
        alert.fadeOut();
    }, 4000);
}