package com.cukesrepo.controller;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.rendersnake.RenderableWrapper;

import java.io.IOException;

public class SiteLayoutWrapper extends RenderableWrapper {

    public SiteLayoutWrapper(Renderable component) {
        super(component);
    }

    @Override
    public void renderOn(HtmlCanvas html) throws IOException {
        html
                .html()
                .head()
                ._head()
                .body()
                .render(this.component)
                ._body()
                ._html();
    }
}