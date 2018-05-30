/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MiasTestModule } from '../../../test.module';
import { ProcedureOrderDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order-delete-dialog.component';
import { ProcedureOrderService } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.service';

describe('Component Tests', () => {

    describe('ProcedureOrder Management Delete Component', () => {
        let comp: ProcedureOrderDeleteDialogComponent;
        let fixture: ComponentFixture<ProcedureOrderDeleteDialogComponent>;
        let service: ProcedureOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [ProcedureOrderDeleteDialogComponent],
                providers: [
                    ProcedureOrderService
                ]
            })
            .overrideTemplate(ProcedureOrderDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
