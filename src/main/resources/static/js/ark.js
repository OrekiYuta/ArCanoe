/*
* 提交回复
*/
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content= $("#input-" +commentId).val();
    comment2target(commentId, 2, content);
}

/*
* 展开二级评论
*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        //折叠二级评论
        comments.removeClass("in");
        //移除二级评论标记状态
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else{

        var subCommentContainer = $("#comment-"+ id);

        if(subCommentContainer.children().length !=1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");

        }else {
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {
                    var commentDiv = $("<div/>", {
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    });
                        var mediaDiv = $("<div/>",{
                            "class":"media"
                        });
                            var mediaLeftDiv = $("<div/>",{
                                "class":"media-left"
                            });
                                var avatarDiv = $("<img/>",{
                                    "class":"media-object img-circle avatar-wh",
                                    "src":comment.user.avatarUrl
                                });
                            var mediaBodyDiv = $("<div/>",{
                                "class":"media-body"
                            });
                                var H5commentNameElement =$("<h5/>",{
                                    "class":"media-heading",
                                    html: comment.user.name
                                });

                                var commentElement =$("<div/>",{
                                    html: comment.content
                                });
                                var replyTimeElement =$("<div/>",{
                                    "class":"pull-right icon-desc",
                                    html: moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                                });



                    commentDiv.append(mediaDiv);
                        mediaDiv.append(mediaLeftDiv);
                            mediaLeftDiv.append(avatarDiv);
                        mediaDiv.append(mediaBodyDiv);
                            mediaBodyDiv.append(H5commentNameElement,commentElement,replyTimeElement);

                    subCommentContainer.prepend(commentDiv);
                })
            })


            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }

    }

}

function comment2target(targetId, type, content) {

    if(!content){
        alert("Content can not be null");
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
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

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    console.log(value,previous);
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $('#selectTag').show();
}