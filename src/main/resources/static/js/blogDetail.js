let selectedImage = null;
let selectedEmoji = null;

function addComment(event, parentId = null) {
    event.preventDefault();

    let contentForm = $('#commentContent').val();
    let postID = $('#postId').val();
    let data = new FormData();

    let commentRequest = {
        content: contentForm,
        post_id: postID,
        id_parent: parentId
    };

    data.append('commentRequest', new Blob([JSON.stringify(commentRequest)], { type: 'application/json' }));

    if (selectedImage) {
        data.append('image', selectedImage);
    }

    let url = `/api/v1/comments`;

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        processData: false,
        contentType: false,
        enctype: 'multipart/form-data',
        success: function () {
            $('#commentContent').val('');
            $('#imagePreview').hide();
            selectedImage = null;
            document.getElementById('previewArea').classList.add('d-none');
            reloadToComment(postID);
        },
        error: function (error) {
            console.log(error);
            alert("Error: " + error.responseText);
        }
    });
}

function reloadToComment(postID) {
    let url = `/api/v1/comments/${postID}`;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            let contentComment = `
                <h2 class="mb-4">Comments (<span id="totalComments"></span>)</h2>
            `;

            data.forEach((comment, index) => {
                contentComment += `
                    <div class="comment card mb-3">
                        <div class="card-body">
                            <div class="d-flex">
                                <img src="${comment.account.image}" alt="Avatar" class="rounded-circle me-3 comment-avatar">
                                <div class="flex-grow-1">
                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                        <h5 class="card-title mb-0">${comment.name_user}</h5>
                                        <small class="text-muted comment-metadata">
                                            <i class="bi bi-clock"></i> ${new Date(comment.created_at).toLocaleString()}
                                        </small>
                                    </div>
                                    <p class="card-text">${comment.content}</p>
                                     ${comment.image ? `<img style="margin-bottom: 10px" src="${comment.image}" alt="Comment Image" class="img-fluid">` : ''}
                                    <div class="d-flex justify-content-between align-items-center">
                                        <button class="btn btn-sm btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#replyForm-${comment.id}">Reply</button>
                                        <div>
                                            <button class="btn btn-sm btn-outline-secondary me-2">
                                                <i class="bi bi-hand-thumbs-up"></i> 5
                                            </button>
                                            <button class="btn btn-sm btn-outline-secondary">
                                                <i class="bi bi-hand-thumbs-down"></i> 0
                                            </button>
                                        </div>
                                    </div>
                                    <div class="collapse mt-3" id="replyForm-${comment.id}">
                                        <form onsubmit="submitReply(event, ${comment.id})">
                                            <div class="mb-3">
                                                <textarea class="form-control" rows="3" placeholder="Write your reply..."></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-primary btn-sm">Submit Reply</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        ${comment.replyComment && comment.replyComment.length > 0 ? `
                            <div>
                                ${comment.replyComment.map(reply => `
                                    <div class="card-body border-top">
                                        <div class="d-flex">
                                            <div class="flex-grow-0 me-3">
                                                <div style="width: 32px; height: 32px;"></div>
                                            </div>
                                            <div class="flex-grow-1">
                                                <div class="d-flex">
                                                    <img src="${reply.account.image}" alt="Avatar" class="rounded-circle me-3" style="width: 48px; height: 48px;">
                                                    <div class="flex-grow-1">
                                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                                            <h6 class="card-title mb-0">${reply.name_user}</h6>
                                                            <small class="text-muted comment-metadata">
                                                                <i class="bi bi-clock"></i> ${new Date(reply.created_at).toLocaleString()}
                                                            </small>
                                                        </div>
                                                        <p class="card-text">${reply.content}</p>
                                                            ${comment.image ? `<img src="${reply.image}" alt="Comment Image" class="img-fluid">` : ''}
                                                        <div class="d-flex justify-content-between align-items-center mt-2">
                                                            <button class="btn btn-sm btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#replyForm-reply-${reply.id}">Reply</button>
                                                            <div>
                                                                <button class="btn btn-sm btn-outline-secondary me-2">
                                                                    <i class="bi bi-hand-thumbs-up"></i> 3
                                                                </button>
                                                                <button class="btn btn-sm btn-outline-secondary">
                                                                    <i class="bi bi-hand-thumbs-down"></i> 0
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <div class="collapse mt-3" id="replyForm-reply-${reply.id}">
                                                            <form onsubmit="submitReply(event, ${comment.id})">
                                                                <div class="mb-3">
                                                                    <textarea class="form-control" rows="3" placeholder="Write your reply..."></textarea>
                                                                </div>
                                                                <button type="submit" class="btn btn-primary btn-sm">Submit Reply</button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                `).join('')}
                            </div>
                        ` : ''}
                    </div>
                `;
            });

            $('#main_comment').html(contentComment);
            reloadTotalComment(postID);
        },
        error: function (error) {
            console.log(error);
            alert("Error: " + error.responseText);
        }
    });
}

function reloadTotalComment(postId) {
    let url = `/api/v1/comments/total/${postId}`;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            $('#totalComments').text(`${data}`);
        },
        error: function (error) {
            console.log(error);
            alert("Error: " + error.responseText);
        }
    })
}

function handleImageSelect(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            selectedImage = file;
            const preview = document.getElementById('imagePreview');
            preview.src = e.target.result;
            preview.style.display = 'block';
            document.getElementById('previewArea').classList.remove('d-none');
        };
        reader.readAsDataURL(file);
    }
}

function addEmoji(emoji) {
    selectedEmoji = emoji;
    const textArea = document.getElementById('commentContent');
    textArea.value += emoji;
    document.querySelector('.selected-icon').textContent = emoji;
}

function submitReply(event, parentId) {
    event.preventDefault();

    // Lấy nội dung từ textarea của form reply
    let content = $(event.target).find('textarea').val();
    let postId = $('#postId').val();

    // Tạo FormData object để gửi dữ liệu
    let data = new FormData();

    // Tạo object chứa thông tin comment
    let commentRequest = {
        content: content,
        post_id: postId,
        id_parent: parentId
    };

    // Append commentRequest vào FormData dưới dạng Blob
    data.append('commentRequest', new Blob([JSON.stringify(commentRequest)], { type: 'application/json' }));

    let url = `/api/v1/comments`;

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        processData: false,
        contentType: false,
        enctype: 'multipart/form-data',
        success: function () {
            // Reset form sau khi gửi thành công
            $(event.target).find('textarea').val('');
            reloadToComment(postId);
        },
        error: function (error) {
            console.log(error);
            alert("Error: " + error.responseText);
        }
    });
}