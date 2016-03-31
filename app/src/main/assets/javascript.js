/*
	JavaScript for Zhihu Android WebView
*/

var DEFAULT_IMAGE_URI = "file:///android_asset/default_pic_content_image.png";

var DEFAULT_LOADING_IMAGE_URI = "file:///android_asset/default_pic_content_image_loading.gif";

var DEFAULT_LOADING_FAILED_IMAGE_URI = "file:///android_asset/default_pic_content_image_failed.png";

function onLoaded()
{
	var allImage = document.querySelectorAll("IMG");

	allImage = Array.prototype.slice.call(allImage, 0);

	allImage.forEach(function(image)
	{
		if (image.src == DEFAULT_LOADING_IMAGE_URI)
		{
			ZhihuAndroid.loadImage(image.getAttribute("original-src"));
		}
	});

	setFontSize(ZhihuAndroid.getFontSize());
}

function setContentPadding(pLeft, pTop, pRight, pBottom)
{
	var content = document.getElementById("content");

	content.style.paddingLeft = pLeft;
	content.style.paddingTop = pTop;
	content.style.paddingRight = pRight;
	content.style.paddingBottom = pBottom;
};

function setFontSize(pFontSize)
{
	document.body.style.fontSize = pFontSize;
};

function onImageClick(pImage)
{
	if (pImage.src == DEFAULT_IMAGE_URI || pImage.src == DEFAULT_LOADING_FAILED_IMAGE_URI)
	{
		pImage.src = DEFAULT_LOADING_IMAGE_URI;

		ZhihuAndroid.loadImage(pImage.getAttribute("original-src"));
	}
	else if (pImage.src == DEFAULT_LOADING_IMAGE_URI)
	{
		//nothing
	}
	else
	{
		ZhihuAndroid.openImage(pImage.getAttribute("original-src"));
	}
};

function onImageLoadingFailed(pUrl)
{
	var allImage = document.querySelectorAll("IMG");

	allImage = Array.prototype.slice.call(allImage, 0);

	allImage.forEach(function(image)
	{
		if (image.getAttribute("original-src") == pOldUrl || image.getAttribute("original-src") == decodeURIComponent(pUrl))
		{
			image.src = DEFAULT_LOADING_FAILED_IMAGE_URI;
		}
	});
}

function onImageLoadingComplete(pOldUrl, pNewUrl)
{
	var allImage = document.querySelectorAll("IMG");

	allImage = Array.prototype.slice.call(allImage, 0);

	allImage.forEach(function(image)
	{
		if (image.getAttribute("original-src") == pOldUrl || image.getAttribute("original-src") == decodeURIComponent(pOldUrl))
		{
			image.src = pNewUrl;
		}
	});
}

function createPlayer(box) {
	var inner = $(box).find('.video-box-inner').addClass('clear-fix').first()
	var url = $(box).find('.video-url').first()

	var link = $('<a />', {href: url.html(), class: "video-box-button"}).on('click', function(e){e.stopPropagation()}).append(inner).insertAfter(box);
	$(box).remove()
}

$(document).ready(function(){
  $('.video-box').each(function(index, box){
    createPlayer(box)
  })
});

document.addEventListener("touchstart", function() {},false);