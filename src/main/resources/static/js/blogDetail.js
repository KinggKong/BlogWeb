let selectedImage = null;
let selectedEmoji = null;
let replySelectedImages = {};

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
    data.append('commentRequest', new Blob([JSON.stringify(commentRequest)], {type: 'application/json'}));
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
                                        <form onsubmit="submitReply(event, ${comment.id}); return false;">
                                            <div class="mb-3">
                                                <textarea class="form-control reply-content" rows="3" placeholder="Write your reply..."></textarea>
                                            </div>
                                            <div id="previewArea-${comment.id}" class="mb-3 d-none">
                                                <div class="position-relative">
                                                    <img id="imagePreview-${comment.id}" class="reply-image-preview img-fluid rounded mb-2" style="max-height: 200px; display: none;">
                                                    <button type="button" class="btn-close position-absolute top-0 end-0 m-1" onclick="removeReplyImage(${comment.id})"></button>
                                                </div>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="d-flex gap-2">
                                                    <div class="position-relative">
                                                        <button type="button" class="btn btn-outline-secondary btn-sm" onclick="document.querySelector('#imageInput-${comment.id}').click()">
                                                            <i class="bi bi-image"></i> Add Image
                                                        </button>
                                                        <input type="file" id="imageInput-${comment.id}" class="reply-image-input" accept="image/*"
                                                               style="display: none;" onchange="handleReplyImageSelect(event, ${comment.id})">
                                                    </div>
                                                    <div class="dropdown">
                                                        <button type="button" class="btn btn-outline-secondary btn-sm dropdown-toggle"
                                                                data-bs-toggle="dropdown">
                                                            <i class="bi bi-emoji-smile"></i>
                                                        </button>
                                                        <div class="dropdown-menu p-2 emoji-picker">
                                                            <span onclick="addEmojiToReply('😊', ${comment.id})">😊</span>
                                                            <span onclick="addEmojiToReply('👍', ${comment.id})">👍</span>
                                                            <span onclick="addEmojiToReply('❤️', ${comment.id})">❤️</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <button type="submit" class="btn btn-primary btn-sm">Submit Reply</button>
                                            </div>
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
                                                        ${reply.image ? `<img style="margin-bottom: 10px" src="${reply.image}" alt="Reply Image" class="img-fluid">` : ''}
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
                                                            <form onsubmit="submitReply(event, ${comment.id}, true); return false;">
                                                                <div class="mb-3">
                                                                    <textarea class="form-control reply-content" rows="3" placeholder="Write your reply..."></textarea>
                                                                </div>
                                                          <div id="previewArea-reply-${reply.id}" class="mb-3 d-none">
    <div class="position-relative">
        <img id="imagePreview-reply-${reply.id}" class="reply-image-preview img-fluid rounded mb-2" style="max-height: 200px; display: none;">
        <button type="button" class="btn-close position-absolute top-0 end-0 m-1" onclick="removeReplyImage(${reply.id}, true)"></button>
    </div>
</div>
                                                                <div class="d-flex justify-content-between align-items-center">
                                                                    <div class="d-flex gap-2">
                                                                        <div class="position-relative">
                                                                            <button type="button" class="btn btn-outline-secondary btn-sm" onclick="document.querySelector('#imageInput-reply-${reply.id}').click()">
                                                                                <i class="bi bi-image"></i> Add Image
                                                                            </button>
                                                                           <input type="file" id="imageInput-reply-${reply.id}" class="reply-image-input" accept="image/*"
                                                                             style="display: none;" onchange="handleReplyImageSelect(event, ${reply.id}, true)">
                                                                        </div>
                                                                        <div class="dropdown">
                                                                            <button type="button" class="btn btn-outline-secondary btn-sm dropdown-toggle"
                                                                                    data-bs-toggle="dropdown">
                                                                                <i class="bi bi-emoji-smile"></i>
                                                                            </button>
                                                                            <div class="dropdown-menu p-2 emoji-picker">
                                                                                <span onclick="addEmojiToReply('😊', ${reply.id})">😊</span>
                                                                                <span onclick="addEmojiToReply('👍', ${reply.id})">👍</span>
                                                                                <span onclick="addEmojiToReply('❤️', ${reply.id})">❤️</span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <button type="submit" class="btn btn-primary btn-sm">Submit Reply</button>
                                                                </div>
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
        reader.onload = function (e) {
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

function handleReplyImageSelect(event, commentId, isReplyToReply = false) {
    const file = event.target.files[0];
    if (file) {
        const replyKey = isReplyToReply ? `reply-${commentId}` : `main-${commentId}`;
        replySelectedImages[replyKey] = file;
        const previewAreaId = isReplyToReply ? `previewArea-reply-${commentId}` : `previewArea-${commentId}`;
        const imagePreviewId = isReplyToReply ? `imagePreview-reply-${commentId}` : `imagePreview-${commentId}`;

        const previewArea = document.getElementById(previewAreaId);
        const imagePreview = document.getElementById(imagePreviewId);

        if (previewArea && imagePreview) {
            previewArea.classList.remove('d-none');
            imagePreview.style.display = 'block';

            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    }
}

function submitReply(event, parentId, isReplyToReply = false) {
    console.log("Image for parentId:", parentId, replySelectedImages);
    event.preventDefault();
    const form = event.target;
    const content = form.querySelector('.reply-content').value;
    const postId = document.getElementById('postId').value;

    const data = new FormData();
    const commentRequest = {
        content: content,
        post_id: postId,
        id_parent: parentId
    };
    data.append('commentRequest', new Blob([JSON.stringify(commentRequest)], {
        type: 'application/json'
    }));
    const replyKey = isReplyToReply ? `reply-${parentId}` : `main-${parentId}`;
    if (replySelectedImages[replyKey]) {
        data.append('image', replySelectedImages[replyKey]);
    }
    $.ajax({
        type: "POST",
        url: "/api/v1/comments",
        data: data,
        processData: false,
        contentType: false,
        enctype: 'multipart/form-data',
        success: function () {
            form.querySelector('.reply-content').value = '';
            removeReplyImage(parentId, isReplyToReply);
            reloadToComment(postId);
            const replyFormId = isReplyToReply ? `replyForm-reply-${parentId}` : `replyForm-${parentId}`;
            const replyForm = document.getElementById(replyFormId);
            if (replyForm) {
                const bsCollapse = new bootstrap.Collapse(replyForm);
                bsCollapse.hide();
            }
        },
        error: function (error) {
            console.log(error);
            alert("Error: " + error.responseText);
        }
    });
}

function addEmojiToReply(emoji, commentId) {
    const textarea = document.querySelector(`#replyForm-${commentId} .reply-content`);
    const cursorPos = textarea.selectionStart;
    const textBefore = textarea.value.substring(0, cursorPos);
    const textAfter = textarea.value.substring(cursorPos);
    textarea.value = textBefore + emoji + textAfter;
    textarea.selectionStart = cursorPos + emoji.length;
    textarea.selectionEnd = cursorPos + emoji.length;
    textarea.focus();
}

function removeReplyImage(commentId, isReplyToReply = false) {
    const replyKey = isReplyToReply ? `reply-${commentId}` : `main-${commentId}`;
    delete replySelectedImages[replyKey];
    const previewAreaId = isReplyToReply ? `previewArea-reply-${commentId}` : `previewArea-${commentId}`;
    const imagePreviewId = isReplyToReply ? `imagePreview-reply-${commentId}` : `imagePreview-${commentId}`;
    const previewArea = document.getElementById(previewAreaId);
    const imagePreview = document.getElementById(imagePreviewId);
    if (previewArea && imagePreview) {
        previewArea.classList.add('d-none');
        imagePreview.style.display = 'none';
        imagePreview.src = '';
    }
}

function toggleLike(idPost, idAccount) {
    let url = `/post/toggleLike?idPost=${idPost}&idAccount=${idAccount}`;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "JSON",
        success: function (response) {
            const likeIcon = $('#like-icon');
            if (response.liked) {
                likeIcon.removeClass('bi-heart').addClass('bi-heart-fill text-danger');
            } else {
                likeIcon.removeClass('bi-heart-fill text-danger').addClass('bi-heart');
            }
        },
        error: function (error) {
            console.error(error);
            alert(error.responseText);
        }
    });
}

