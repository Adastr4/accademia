import { element, by, ElementFinder } from 'protractor';

export class SourceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-source div table .btn-danger'));
  title = element.all(by.css('jhi-source div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class SourceUpdatePage {
  pageTitle = element(by.id('jhi-source-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  sourceidInput = element(by.id('field_sourceid'));
  descriptionInput = element(by.id('field_description'));
  fonteSelect = element(by.id('field_fonte'));
  dataInput = element(by.id('field_data'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setSourceidInput(sourceid: string): Promise<void> {
    await this.sourceidInput.sendKeys(sourceid);
  }

  async getSourceidInput(): Promise<string> {
    return await this.sourceidInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setFonteSelect(fonte: string): Promise<void> {
    await this.fonteSelect.sendKeys(fonte);
  }

  async getFonteSelect(): Promise<string> {
    return await this.fonteSelect.element(by.css('option:checked')).getText();
  }

  async fonteSelectLastOption(): Promise<void> {
    await this.fonteSelect.all(by.tagName('option')).last().click();
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SourceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-source-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-source'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
