/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MiasTestModule } from '../../../test.module';
import { DrugOrderDialogComponent } from '../../../../../../main/webapp/app/entities/drug-order/drug-order-dialog.component';
import { DrugOrderService } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.service';
import { DrugOrder } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.model';
import { DoctorService } from '../../../../../../main/webapp/app/entities/doctor';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug';
import { PrescriptionService } from '../../../../../../main/webapp/app/entities/prescription';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient';

describe('Component Tests', () => {

    describe('DrugOrder Management Dialog Component', () => {
        let comp: DrugOrderDialogComponent;
        let fixture: ComponentFixture<DrugOrderDialogComponent>;
        let service: DrugOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [DrugOrderDialogComponent],
                providers: [
                    DoctorService,
                    DrugService,
                    PrescriptionService,
                    PatientService,
                    DrugOrderService
                ]
            })
            .overrideTemplate(DrugOrderDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugOrderDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DrugOrder(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.drugOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'drugOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DrugOrder();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.drugOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'drugOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
