<script type="text/javascript">
YAHOO.example.init = function( ){
    var tabView = new YAHOO.widget.TabView( { id: 'demo' } );
    
    tabView.addTab( new YAHOO.widget.Tab({
        label: 'lorem',
        content: '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat.</p>',
        active: true
    }));
    
    tabView.addTab( new YAHOO.widget.Tab({
        label: 'ipsum',
        content: '<ul><li><a href="#">Lorem ipsum dolor sit amet.</a></li><li><a href="#">Lorem ipsum dolor sit amet.</a></li><li><a href="#">Lorem ipsum dolor sit amet.</a></li><li><a href="#">Lorem ipsum dolor sit amet.</a></li></ul>'
    }));
    
    tabView.addTab( new YAHOO.widget.Tab({
        label: 'dolor',
        content: '<form action="#"><fieldset><legend>Lorem Ipsum</legend><label for="foo"> <input id="foo" name="foo"></label><input type="submit" value="submit"></fieldset></form>'
    }));
    
    YAHOO.util.Event.onContentReady('doc', function() {
        tabView.appendTo(this); /* append to #doc */
    });
};

YAHOO.example.init();
</script>
