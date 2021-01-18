Vue.component('comment-view', {
    props: {
        comment: Object
    },
    template: '<div>' +
                '<div class="comment-block" :key="comment.commentId">' +
                    '<div class="media border border-primary bg-light" v-if="comment.replyParentId == null">' +
                        '<img class="d-flex align-self-start mr-3">' +
                        '<div class="media-body">' +
                            '<h5 class="mt-0">{{comment.userId}}</h5>' +
                            '<a class="text-dark" :href="\'/\' + comment.userId + \'/comment/\' + comment.commentId">' +
                                '<p>{{comment.comment}}</p>' +
                            '</a>' +
                            '<div>' +
                                '<span class="material-icons">chat</span>' +
                                '{{comment.replies.length}}' +
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
            intervalId: undefined
       }
    },
    mounted() {
        this.repeatAllCommentGet();
    },
    beforeDestroy () {
        console.log('clearInterval');
        clearInterval(this.intervalId);
    },
    methods: {
        repeatAllCommentGet: function() {
            var pathUserId = '';
            var path = location.pathname;
            var regex = '/[A-Za-z0-9_-]*/';
            pathUserId = path.match(regex);
            console.log(pathUserId);

            axios
                .get('/rest-api/user-comment' + pathUserId + 'get')
                .then(response => {
                    this.comments = response.data;
                    this.comments.forEach(comment => {
                        this.allReplyCommentGet(comment);
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
        }
    }
})