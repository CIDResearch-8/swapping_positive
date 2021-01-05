Vue.component('comment-view', {
    props: {
        comment: Object
    },
    mounted() {
        axios
            .get('/rest-api/comment/' + this.comment.commentId + '/get')
            .then(response => {
                this.comment = response.data;
                this.allReplyCommentGet(this.comment);
                console.log(this.comment);
                console.log('getting comment');
            })
            .catch(err => {
                console.log(err);
        });
    },
    template: '<div>' +
                '<div class="comment-block" @load="">' +
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
});


var app = new Vue({
    el: '.comment-view',
    data() {
        return {
            comment: {}
       }
    },
    mounted() {
        this.commentGet();
    },
    methods: {
        commentGet: function() {
            var path = location.pathname;
            var regex = 'comment/[0-9]*';
            var pathCommentId = path.match(regex);
            console.log(pathCommentId);

            axios
                .get('/rest-api/' + pathCommentId[0] + '/get')
                .then(response => {
                    this.comment = response.data;
                    this.allReplyCommentGet(this.comment);
                    console.log(this.comment);
                    console.log('getting comment');
                })
                .catch(err => {
                    console.log(err);
            });
        },
        specifiedCommentGet: function(commentId) {
            axios
                .get('/rest-api/comment/' + commentId + '/get')
                .then(response => {
                    this.comment = response.data;
                    this.allReplyCommentGet(this.comment);
                    console.log(this.comment);
                    console.log('getting comment');
                })
                .catch(err => {
                    console.log(err);
            });
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