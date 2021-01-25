Vue.component('comment-view', {
    props: {
        comment: Object
    },
    template: '<div>' +
                '<div class="comment-block" :key="comment.commentId">' +
                    '<div class="media border border-primary bg-light" v-if="comment.replyParentId == null">' +
                        '<img class="d-flex align-self-start mr-3" :src="comment.iconUri" :href="comment.iconUri" height="50" weight="50" >' +
                        '<div class="media-body">' +
                            '<h5 class="mt-0"><a class="text-dark" :href="\'/\' + comment.userId + \'/mypage\'">{{comment.username}}</a></h5>' +
                            '<a class="text-dark" :href="\'/\' + comment.userId + \'/comment/\' + comment.commentId">' +
                                '<p>{{comment.comment}}</p>' +
                            '</a>' +
                            '<div>' +
                                '<a class="text-dark" :href="\'/\' + comment.userId + \'/comment/\' + comment.commentId">' +
                                    '<span class="material-icons">chat</span>' +
                                    '{{comment.replies.length}}' +
                                '</a>' +
                                '<span class="text-secondary float-right">{{comment.date}}</span>' +
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
            loginUser: "",
            comments: [],
            intervalId: undefined
       }
    },
    mounted() {
        axios
            .get('/rest-api/login-user-id/get')
            .then(response => {
                this.loginUser = response.data;
                console.log('getting login user ' + this.loginUser);
                this.repeatAllCommentGet(this.loginUser);
            })
            .catch(err => {
                console.log('get error');
        });
    },
    beforeDestroy () {
        console.log('clearInterval');
        clearInterval(this.intervalId);
    },
    methods: {
        repeatAllCommentGet: function(loginUser) {
            axios
                .get('/rest-api/user-comment/' + loginUser + '/get')
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
        }
    }
})