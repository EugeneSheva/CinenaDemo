document.addEventListener('DOMContentLoaded', function() {
    jQuery(document).ready(function () {
        ImgUpload();
    });

    function ImgUpload() {
        var imgWrap1 = "";
        var imgArray1 = [];

        $('.upload__inputfile1').each(function () {
            $(this).on('change', function (e) {
                imgWrap1 = $(this).closest('.upload__box1').find('.upload__img-wrap1');
                var maxLength = $(this).attr('data-max_length1');

                var files = e.target.files;
                var filesArr1 = Array.prototype.slice.call(files); //смотреть сдесь
                var iterator = 0;
                filesArr1.forEach(function (f, index) {

                    if (!f.type.match('image.*')) {
                        return;
                    }

                    if (imgArray1.length > maxLength) {
                        return false
                    } else {
                        var len = 0;
                        for (var i = 0; i < imgArray1.length; i++) {
                            if (imgArray1[i] !== undefined) {
                                len++;
                            }
                        }
                        if (len > maxLength) {
                            return false;
                        } else {
                            imgArray1.push(f);

                            var reader = new FileReader();
                            reader.onload = function (e) {
                                var html = "<div class='upload__img-box1'><div style='background-image: url(" + e.target.result + ")' data-number='" + $(".upload__img-close").length + "' data-file='" + f.name + "' class='img-bg1'><div class='upload__img-close1' ></div></div></div>" ;
                                imgWrap1.append(html);
                                iterator++;
                            }
                            reader.readAsDataURL(f);
                        }
                    }
                });
            });
        });

        $('body').on('click', ".upload__img-close1", function (e) {
            var file = $(this).parent().data("file");
            for (var i = 0; i < imgArray1.length; i++) {
                if (imgArray1[i].name === file) {
                    imgArray1.splice(i, 1);
                    break;
                }
            }
            $(this).parent().parent().remove();
        });
    }
});

