
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if(!content){
        alert("Content can not be null");
        return;
    }

    $.ajax({
       type:"POST",
       url:"/comment",
       contentType:"application/json",
       data:JSON.stringify({
           "parentId":questionId,
           "content":content,
           "type":1
       }),
       success: function (response) {
           if(response.code == 200){
               window.location.reload();
               // $("#comment_content").hide();
           }else {
               if (response.code == 2003) {
                   var isAccepted = confirm(response.message);
                   if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=78df81e05d037ebe3815&rediret_url=http://localhost:2222/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                   }
               }else {
                   alert(response.message);
               }
           }
           console.log(response);
       },
       dataType:"json"
    });

}