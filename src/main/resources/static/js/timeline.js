Vue.component('comment-view', {
    props: {
        comment: Object,
        nowLoginUser: String
    },
    methods: {
        submit: function(comment) {
            axios
                .delete('/rest-api/comment/' + comment.commentId + '/delete')
                .then(() => {
                    console.log("success delete");
                    this.inputText = '';
                })
                .catch(err => {
                    console.log('submit error');
                });
        }
    },
    template: '<div>' +
                '<div class="comment-block" :key="comment.commentId">' +
                    '<div class="media border border-primary bg-light" v-if="comment.replyParentId == null">' +
                        '<img class="d-flex align-self-start mr-3" :src="comment.iconUri" :href="comment.iconUri" height="50" weight="50" >' +
                        '<div class="media-body">' +
                            '<div class="d-flex">' +
                                '<h5 class="p-2 flex-grow-1 mt-0"><a class="text-dark" :href="\'/\' + comment.userId + \'/mypage\'" method="delete">{{comment.username}}</a></h5>' +
                                '<div class="p-2 float-right">' +
                                  '<button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
                                    '<span class="material-icons"> more_vert </span>' +
                                  '</button>' +
                                  '<div class="dropdown-menu" aria-labelledby="dropdownMenu1">' +
                                    '<button type="button" class="dropdown-item btn btn-link" data-toggle="modal" data-target="#exampleModal" :href="\'/comment/\' + comment.commentId + \'/delete\'" :class="{disabled: comment.userId != nowLoginUser}">コメントの削除</button>' +
                                  '</div>' +
                                '</div>' +
                            '</div>' +
                            '<a class="text-dark" :href="\'/\' + comment.userId + \'/comment/\' + comment.commentId">' +
                                '<p>{{comment.comment}}</p>' +
                            '</a>' +
                            '<div>' +
                                '<a class="text-dark" :href="\'/\' + comment.userId + \'/comment/\' + comment.commentId">' +
                                    '<span class="material-icons">chat</span>' +
                                    '{{comment.replies.length}}' +
                                '</a>' +
                                '<span class="text-secondary float-right mr-1">{{comment.date}}</span>' +
                            '</div>' +
                        '</div>' +
                   '</div>' +
                   '<!-- Modal -->' +
                   '<div class="modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
                     '<div class="modal-dialog" role="document">' +
                       '<div class="modal-content">' +
                         '<div class="modal-header">' +
                         '<h5 class="modal-title" id="exampleModalLabel">本当にコメントを削除しますか？</h5>' +
                           '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                             '<span aria-hidden="true">&times;</span>' +
                           '</button>' +
                         '</div>' +
                         '<div class="modal-footer" >' +
                           '<input type="button" class="btn btn-secondary" data-dismiss="modal" value="しない">' +
                           '<a href="" @click="submit(comment)" class="btn btn-primary">削除する</a>' +
                         '</div>' +
                       '</div>' +
                     '</div>' +
                   '</div>' +
                '</div>' +
              '</div>'
})

var app = new Vue({
    el: '.comment-view',
    data() {
        return {
            comments: [],
            intervalId: undefined,
            loginUser: ""
       }
    },
    mounted() {
        this.loginUserGet();
        this.repeatAllCommentGet();
        this.intervalId = setInterval(() => {
            if (document.visibilityState == 'visible') {
                this.repeatAllCommentGet();
            }
        }, 5000);
    },
    beforeDestroy () {
        console.log('clearInterval');
        clearInterval(this.intervalId);
    },
    methods: {
        repeatAllCommentGet: function() {
            axios
                .get('/rest-api/all-comment/get')
                .then(response => {
                    this.comments = response.data;
                    this.comments.forEach(comment => {
                        this.allReplyCommentGet(comment);
                        this.iconUrlGet(comment);
                        this.usernameGet(comment);
                        this.formattingDate(comment);
                    });
                    console.log('getting all comments');
                })
                .catch(err => {
                    console.log('get error');
            });
            console.log(this.comments);
        },
        allReplyCommentGet: function(comment) {
            axios
                .get('/rest-api/reply-comment/' + comment.commentId + '/get')
                .then(response => {
                    this.$set(comment, 'replies', response.data);
                    console.log('getting all replies');
                })
                .catch(err => {
                    console.log(err.response);
            });
        },
        iconUrlGet: function(comment) {
            axios
                .get('/rest-api/' + comment.userId + '/icon-uri/get')
                .then(response => {
                    this.$set(comment, 'iconUri', response.data);
                    console.log('getting icon uri');
                })
                .catch(err => {
                    console.log(err.response);
            });
        },
        usernameGet: function(comment) {
            axios
                .get('/rest-api/' + comment.userId + '/username/get')
                .then(response => {
                    this.$set(comment, 'username', response.data);
                    console.log('getting username');
                })
                .catch(err => {
                    console.log(err.response);
            });
        },
        formattingDate: function(comment) {
            var newDate = new Date(comment.date);
            this.$set(comment, 'date', newDate.toLocaleString('ja-JP'));
        },
        loginUserGet: function() {
            axios
                .get('/rest-api/login-user-id/get')
                .then(response => {
                    this.loginUser = response.data;
                    console.log('getting user id is ' + this.loginUser);
                })
                .catch(err => {
                    console.log('get error');
            });
        }
    }
})