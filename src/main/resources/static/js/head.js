//JS
layui.use(['element', 'layer', 'util'], function () {
    var element = layui.element
        , layer = layui.layer
        , util = layui.util
    // ,$ = layui.$;

});

function switchCharacter(id) {
    request.post("/api/switchCharacter", {
        id: id
    })
        .then(response => {
            location.reload()
        })
        .catch(err => {
            layui.alert("切换失败")
        })
}