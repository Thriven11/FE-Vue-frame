/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

 ;(function($) {

    /**
     * CUI.rte.ui.cui.CQLinkBaseDialog
     *
     * @ignore
     * @class
     * @extends CUI.rte.ui.cui.AbstractDialog
     * @private
     */

    
    CUI.rte.ui.cui.CQLinkBaseDialog = new Class({

        extend: CUI.rte.ui.cui.LinkBaseDialog,

        toString: "CQLinkBaseDialog",

        hbTemplate: (function anonymous(data_0) {
            var frag = document.createDocumentFragment();
            var data = data_0;
            var el0 = document.createElement("div");
            el0.className += " rte-dialog-columnContainer";
            var el1 = document.createTextNode("\n    ");
            el0.appendChild(el1);
            var el2 = document.createElement("div");
            el2.className += " rte-dialog-column";
            var el3 = document.createTextNode("\n      ");
            el2.appendChild(el3);
            var el4 = document.createElement("foundation-autocomplete");
            el4.setAttribute("pickersrc", data_0["pickerSrc"]);
            el4.setAttribute("placeholder", CUI["rte"]["Utils"]["i18n"]('dialog.link.path'));
            el4.setAttribute("name", "href");
            el4.setAttribute("details", "custom_selection");
            var el5 = document.createTextNode("\n        ");
            el4.appendChild(el5);
            var el6 = document.createElement("coral-overlay");
            el6.className += " foundation-autocomplete-value foundation-picker-buttonlist";
            el6.setAttribute("data-foundation-picker-buttonlist-src", data_0["suggestionSrc"]);
            el4.appendChild(el6);
            var el7 = document.createTextNode("\n        ");
            el4.appendChild(el7);
            var el8 = document.createElement("coral-taglist");
            el8.setAttribute("foundation-autocomplete-value", "");
            el8.setAttribute("name", "href");
            el4.appendChild(el8);
            var el9 = document.createTextNode("\n      ");
            el4.appendChild(el9);
            el2.appendChild(el4);
            var el10 = document.createTextNode("\n    ");
            el2.appendChild(el10);
            el0.appendChild(el2);
            var el11 = document.createTextNode("\n");
            el0.appendChild(el11);
            frag.appendChild(el0);
            var el12 = document.createTextNode("\n");
            frag.appendChild(el12);
            var el13 = document.createElement("div");
            el13.className += " rte-dialog-columnContainer";
            var el14 = document.createTextNode("\n    ");
            el13.appendChild(el14);
            var el15 = document.createElement("div");
            el15.className += " rte-dialog-column";
            var el16 = document.createElement("label");
            var el17 = document.createTextNode(" ");
            el16.appendChild(el17);
            var el18 = document.createElement("input","coral-textfield");
            el18.setAttribute("is", "coral-textfield");
            el18.setAttribute("data-alt-text", "coral-textfield");
            el18.setAttribute("data-type", "title");
            el18.setAttribute("placeholder", CUI["rte"]["Utils"]["i18n"]('dialog.link.titleFieldPlaceHolder'));
            el18.required = true;  
            el16.appendChild(el18);
            var el19 = document.createTextNode(" ");
            el16.appendChild(el19);
            el15.appendChild(el16);
            var el20 = document.createTextNode(" ");
            el15.appendChild(el20);
            el13.appendChild(el15);
            var el21 = document.createTextNode("\n");
            el13.appendChild(el21);
            frag.appendChild(el13);
            var el22 = document.createTextNode("\n");
            frag.appendChild(el22);
            var el23 = document.createElement("div");
            el23.className += " rte-dialog-columnContainer";
            var el24 = document.createTextNode("\n    ");
            el23.appendChild(el24);
            var el25 = document.createElement("div");
            el25.className += " rte-dialog-column";
            var el26 = document.createTextNode("\n        ");
            el25.appendChild(el26);
            var el27 = this["targetSelect"] = document.createElement("coral-select");
            el27.setAttribute("handle", "targetSelect");
            var el28 = document.createTextNode("\n            ");
            el27.appendChild(el28);
            var el29 = document.createElement("coral-select-item");
            el29.setAttribute("value", "");
            el29.textContent = CUI["rte"]["Utils"]["i18n"]('dialog.link.target');
            el27.appendChild(el29);
            var el30 = document.createTextNode("\n            ");
            el27.appendChild(el30);
            var el31 = document.createElement("coral-select-item");
            el31.setAttribute("value", "_self");
            el31.textContent = CUI["rte"]["Utils"]["i18n"]('dialog.link.same_tab');
            el27.appendChild(el31);
            var el32 = document.createTextNode("\n            ");
            el27.appendChild(el32);
            var el33 = document.createElement("coral-select-item");
            el33.setAttribute("value", "_blank");
            el33.textContent = CUI["rte"]["Utils"]["i18n"]('dialog.link.new_tab');
            el27.appendChild(el33);
            var el34 = document.createTextNode("\n            ");
            el27.appendChild(el34);
            var el35 = document.createElement("coral-select-item");
            el35.setAttribute("value", "_parent");
            el35.textContent = CUI["rte"]["Utils"]["i18n"]('dialog.link.parent_frame');
            el27.appendChild(el35);
            var el36 = document.createTextNode("\n            ");
            el27.appendChild(el36);
            var el37 = document.createElement("coral-select-item");
            el37.setAttribute("value", "_top");
            el37.textContent = CUI["rte"]["Utils"]["i18n"]('dialog.link.top_frame');
            el27.appendChild(el37);
            var el38 = document.createTextNode("\n        ");
            el27.appendChild(el38);
            el25.appendChild(el27);
            var el39 = document.createTextNode("\n    ");
            el25.appendChild(el39);
            el23.appendChild(el25);
            var el40 = document.createTextNode("\n");
            el23.appendChild(el40);
            frag.appendChild(el23);
            var el41 = document.createTextNode("\n");
            frag.appendChild(el41);
            var el42 = document.createElement("div");
            el42.className += " rte-dialog-columnContainer";
            var el43 = document.createTextNode("\n    ");
            el42.appendChild(el43);
            var el44 = document.createElement("div");
            el44.className += " rte-dialog-column rte-dialog-column--rightAligned";
            var el45 = document.createTextNode("\n        ");
            el44.appendChild(el45);
            var el46 = document.createElement("button","coral-button");
            el46.setAttribute("is", "coral-button");
            el46.setAttribute("icon", "close");
            el46.setAttribute("title", CUI["rte"]["Utils"]["i18n"]('dialog.cancel'));
            el46.setAttribute("aria-label", CUI["rte"]["Utils"]["i18n"]('dialog.cancel'));
            el46.setAttribute("iconsize", "S");
            el46.setAttribute("type", "button");
            el46.setAttribute("data-type", "cancel");
            el46.setAttribute("tabindex", "-1");
            el44.appendChild(el46);
            var el47 = document.createTextNode("\n        ");
            el44.appendChild(el47);
            var el48 = document.createElement("button","coral-button");
            el48.setAttribute("is", "coral-button");
            el48.setAttribute("icon", "check");
            el48.setAttribute("title", CUI["rte"]["Utils"]["i18n"]('dialog.apply'));
            el48.setAttribute("aria-label", CUI["rte"]["Utils"]["i18n"]('dialog.apply'));
            el48.setAttribute("iconsize", "S");
            el48.setAttribute("variant", "primary");
            el48.setAttribute("type", "button");
            el48.setAttribute("data-type", "apply");
            el48.setAttribute("tabindex", "-1");
            el48.setAttribute("id", "check_custom_button");
            el48.setAttribute("onclick", "return check_alt_button()");
            el44.appendChild(el48);
            var el49 = document.createTextNode("\n    ");
            el44.appendChild(el49);
            el42.appendChild(el44);
            var el50 = document.createTextNode("\n");
            el42.appendChild(el50);
            frag.appendChild(el42);
            var el51 = document.createTextNode("\n");
            frag.appendChild(el51);
            return frag;
        }),

        initialize: function (config) {
            this.inherited(arguments);
            this.hrefField = this.$dialog.find('foundation-autocomplete')[0];
        },

        construct: function() {
            window["Coral"]["templates"]["RichTextEditor"]["dlg_" + this.getDataType()] = this.hbTemplate;
        },

        dlgToModel: function() {
            this.inherited(arguments);
            if (this.objToEdit) {
                // Convert to a proper URL using URITemplate. Granite.URITemplate acts like a silver bullet.
                // It converts a path to a url, however if path is already a url, it will not double encode it.
                // Refer CQ-4206768
                var href = this.objToEdit.href;
                href = Granite.URITemplate.expand("{+path}", {"path" : href});
                // CQ Antisamy rules remove href with single quote characters
                // Hence, we need to url-encode them as a special case. See CQ-4223011
                href = href.replace(/'/g, '%27');
                this.objToEdit.href = href;
            }
        },

        getDataType: function() {
            return "link";
        }

    });

})(window.jQuery);
//

function check_alt_button(event)
{

	var alt_text = jQuery( "input[placeholder='Alt Text']" ).val();

    if(alt_text == '')
    {

		 $("#check_custom_button").attr("data-type", "apply2"); 
    }
	else
	{
        $("#check_custom_button").attr("data-type", "apply");

		//return false;
	}

}